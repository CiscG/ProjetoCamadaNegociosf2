const BASE = '/api'

const RATING_BY_ID = {
  '75ef00000000000000000001': 4.9,
  '75ef00000000000000000002': 4.8,
  '75ef00000000000000000003': 4.7,
}

async function parseResponse(res) {
  const text = await res.text()

  if (!text) return null

  try {
    return JSON.parse(text)
  } catch {
    return text
  }
}

async function request(path, options = {}) {
  const res = await fetch(`${BASE}${path}`, {
    headers: {
      'Content-Type': 'application/json',
      ...options.headers,
    },
    ...options,
  })

  const data = await parseResponse(res)

  if (!res.ok) {
    const error = new Error(
      data?.erro || data?.mensagem || data?.message || `Erro ${res.status}`
    )
    error.status = res.status
    throw error
  }

  return data
}

async function requestWithFallback(paths, options = {}) {
  let lastError

  for (const path of paths) {
    try {
      return await request(path, options)
    } catch (error) {
      lastError = error
      if (error.status !== 404 && error.status !== 405) {
        throw error
      }
    }
  }

  throw lastError
}

function normalizeUser(user) {
  return {
    id: user.id || user._id,
    nome: user.nome,
    email: user.email,
    tipo: user.tipo || 'hospede',
    dataCadastro: user.dataCadastro || user.data_cadastro,
  }
}

function normalizeLocal(local) {
  const id = local.id || local._id
  const endereco = local.endereco || {}

  return {
    id,
    nome: local.titulo || local.nome,
    cidade: endereco.cidade || local.cidade || '',
    estado: endereco.estado || local.estado || '',
    pais: endereco.pais || local.pais || 'Brasil',
    descricao: local.descricao || '',
    preco_por_noite: local.precoPorNoite ?? local.preco_por_noite ?? 0,
    comodidades: local.comodidades || [],
    anfitriao_id: local.anfitriaoId || local.anfitriao_id,
    anfitriao_nome: local.anfitriaoNome || local.anfitriao_nome,
    data_cadastro: local.dataCadastro || local.data_cadastro,
    avaliacao: Number(local.avaliacao ?? RATING_BY_ID[id] ?? 4.7),
  }
}

function normalizeReserva(reserva) {
  const propriedade = reserva.propriedade ? normalizeLocal(reserva.propriedade) : null

  return {
    id: reserva.id || reserva._id,
    propriedade_id: reserva.propriedadeId || reserva.propriedade_id,
    hospede_id: reserva.hospedeId || reserva.hospede_id,
    desde: reserva.checkin || reserva.desde,
    ate: reserva.checkout || reserva.ate,
    preco_total: reserva.valorTotal ?? reserva.valor_total ?? null,
    status: reserva.status || 'pendente',
    data_reserva: reserva.dataReserva || reserva.data_reserva,
    propriedade,
    local_nome: propriedade?.nome || reserva.local_nome,
    local_cidade: propriedade?.cidade || reserva.local_cidade,
  }
}

function buildQuery(params = {}) {
  const filtered = Object.fromEntries(
    Object.entries(params).filter(([, value]) => value !== '' && value != null)
  )

  const searchParams = new URLSearchParams(filtered)
  return searchParams.toString() ? `?${searchParams.toString()}` : ''
}

export const api = {
  async login(email, senha) {
    const data = await request('/login', {
      method: 'POST',
      body: JSON.stringify({ email, senha }),
    })

    return normalizeUser(data.usuario || data)
  },

  async logout() {
    try {
      await requestWithFallback(['/logout'], { method: 'POST' })
    } catch (error) {
      if (error.status !== 404 && error.status !== 405) {
        throw error
      }
    }
  },

  async getUsuarios() {
    const data = await requestWithFallback(['/usuarios', '/hospedes'])
    return Array.isArray(data) ? data.map(normalizeUser) : []
  },

  async getLocais(params = {}) {
    const { avaliacao, ...queryParams } = params
    const query = buildQuery(queryParams)
    const data = await requestWithFallback([
      `/locais${query}`,
      `/propriedades${query}`,
    ])

    const locais = Array.isArray(data) ? data.map(normalizeLocal) : []

    if (!avaliacao) {
      return locais
    }

    return locais.filter((local) => Number(local.avaliacao) >= Number(avaliacao))
  },

  async createLocal(local) {
    const payload = {
      anfitriaoId: local.anfitriao_id || local.anfitriaoId,
      titulo: local.nome,
      descricao: local.descricao,
      precoPorNoite: Number(local.preco_por_noite),
      endereco: {
        cidade: local.cidade,
        estado: local.estado,
        pais: local.pais || 'Brasil',
      },
      comodidades: local.comodidades || [],
    }

    const data = await requestWithFallback(['/locais', '/propriedades'], {
      method: 'POST',
      body: JSON.stringify(payload),
    })

    return normalizeLocal(data.local || data.propriedade || data)
  },

  async getReservas(params = {}) {
    const query = buildQuery(params)
    const data = await request('/reservas' + query)
    return Array.isArray(data) ? data.map(normalizeReserva) : []
  },

  async createReserva(reserva) {
    const data = await request('/reservas', {
      method: 'POST',
      body: JSON.stringify({
        propriedadeId: reserva.propriedade_id || reserva.local_id,
        hospedeId: reserva.hospede_id,
        checkin: reserva.desde,
        checkout: reserva.ate,
      }),
    })

    return normalizeReserva(data.reserva || data)
  },

  async getOcupacao(id) {
    return requestWithFallback([
      `/locais/${id}/ocupacao`,
      `/propriedades/${id}/ocupacao`,
    ])
  },
}
