const BASE_URL = 'http://localhost:8080/api'

class APIClient {
  async request(method, endpoint, body = null) {
    const options = {
      method,
      headers: {
        'Content-Type': 'application/json',
      },
    }

    if (body) {
      options.body = JSON.stringify(body)
    }

    try {
      const response = await fetch(`${BASE_URL}${endpoint}`, options)
      const data = await response.json()

      if (!response.ok) {
        throw new Error(data.mensagem || data.message || `Erro ${response.status}`)
      }

      return data
    } catch (error) {
      console.error('API Error:', error)
      throw error
    }
  }

  // Viagens
  getViagens() {
    return this.request('GET', '/viagens')
  }

  getViagemById(id) {
    return this.request('GET', `/viagens/${id}`)
  }

  createViagem(viagem) {
    return this.request('POST', '/viagens', viagem)
  }

  updateViagem(id, viagem) {
    return this.request('PUT', `/viagens/${id}`, viagem)
  }

  deleteViagem(id) {
    return this.request('DELETE', `/viagens/${id}`)
  }

  buscarViagensPorDestino(destino) {
    return this.request('GET', `/viagens/destino/${destino}`)
  }

  exportarViagensXML() {
    return fetch(`${BASE_URL}/viagens/export/xml`).then(r => r.text())
  }

  gerarRelatorioViagens() {
    return this.request('POST', '/viagens/relatorio')
  }

  // Clientes
  getClientes() {
    return this.request('GET', '/clientes')
  }

  getClienteById(id) {
    return this.request('GET', `/clientes/${id}`)
  }

  createCliente(cliente) {
    return this.request('POST', '/clientes', cliente)
  }

  updateCliente(id, cliente) {
    return this.request('PUT', `/clientes/${id}`, cliente)
  }

  deleteCliente(id) {
    return this.request('DELETE', `/clientes/${id}`)
  }

  buscarClientePorEmail(email) {
    return this.request('GET', `/clientes/email/${email}`)
  }
}

export const api = new APIClient()
