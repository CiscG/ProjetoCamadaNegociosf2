import { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { api } from '../api/client'
import { useAuth } from '../context/AuthContext'
import { useToast } from '../context/ToastContext'

const INITIAL_FORM = {
  nome: '',
  cidade: '',
  estado: '',
  pais: 'Brasil',
  preco_por_noite: '',
  descricao: '',
  comodidades: '',
}

export default function NewListingPage() {
  const [form, setForm] = useState(INITIAL_FORM)
  const [loading, setLoading] = useState(false)
  const { user, isHost } = useAuth()
  const { addToast } = useToast()
  const navigate = useNavigate()

  const handleChange = (field, value) => {
    setForm((current) => ({ ...current, [field]: value }))
  }

  const handleSubmit = async (event) => {
    event.preventDefault()

    if (!isHost) {
      addToast('Apenas anfitriões podem cadastrar imóveis.', 'error')
      return
    }

    setLoading(true)

    try {
      if (!form.nome.trim()) throw new Error('Informe o nome do imóvel.')
      if (!form.cidade.trim()) throw new Error('Informe a cidade do imóvel.')
      if (!form.estado.trim()) throw new Error('Informe o estado do imóvel.')
      if (!form.preco_por_noite || Number(form.preco_por_noite) <= 0) {
        throw new Error('Informe um preço por noite válido.')
      }

      await api.createLocal({
        ...form,
        anfitriao_id: user.id,
        comodidades: form.comodidades
          .split(',')
          .map((item) => item.trim())
          .filter(Boolean),
      })

      addToast('Imóvel cadastrado com sucesso!', 'success')
      navigate('/dashboard')
    } catch (error) {
      addToast(error.message || 'Erro ao cadastrar imóvel.', 'error')
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="mx-auto max-w-4xl px-4 py-8 sm:px-6 lg:px-8">
      <div className="rounded-[2rem] border border-gray-200 bg-white p-6 shadow-sm sm:p-8">
        <div className="mb-8 max-w-2xl">
          <span className="inline-flex rounded-full bg-rose-50 px-4 py-1 text-xs font-semibold uppercase tracking-[0.3em] text-primary">
            Área do anfitrião
          </span>
          <h1 className="mt-4 text-3xl font-bold text-gray-900">Cadastrar novo imóvel</h1>
          <p className="mt-2 text-sm leading-7 text-gray-500">
            Publique uma hospedagem com cidade, descrição, preço e comodidades para que novos hóspedes possam reservar.
          </p>
        </div>

        <form onSubmit={handleSubmit} className="grid gap-6 md:grid-cols-2">
          <div className="md:col-span-2">
            <label className="mb-2 block text-sm font-medium text-gray-700">Nome do imóvel</label>
            <input
              type="text"
              value={form.nome}
              onChange={(event) => handleChange('nome', event.target.value)}
              placeholder="Ex.: Studio Moderno com varanda"
              className="input-field"
              required
            />
          </div>

          <div>
            <label className="mb-2 block text-sm font-medium text-gray-700">Cidade</label>
            <input
              type="text"
              value={form.cidade}
              onChange={(event) => handleChange('cidade', event.target.value)}
              placeholder="Rio de Janeiro"
              className="input-field"
              required
            />
          </div>

          <div>
            <label className="mb-2 block text-sm font-medium text-gray-700">Estado</label>
            <input
              type="text"
              value={form.estado}
              onChange={(event) => handleChange('estado', event.target.value)}
              placeholder="RJ"
              className="input-field"
              required
            />
          </div>

          <div>
            <label className="mb-2 block text-sm font-medium text-gray-700">País</label>
            <input
              type="text"
              value={form.pais}
              onChange={(event) => handleChange('pais', event.target.value)}
              className="input-field"
            />
          </div>

          <div>
            <label className="mb-2 block text-sm font-medium text-gray-700">Preço por noite (R$)</label>
            <input
              type="number"
              min="1"
              step="0.01"
              value={form.preco_por_noite}
              onChange={(event) => handleChange('preco_por_noite', event.target.value)}
              placeholder="250"
              className="input-field"
              required
            />
          </div>

          <div className="md:col-span-2">
            <label className="mb-2 block text-sm font-medium text-gray-700">Descrição</label>
            <textarea
              value={form.descricao}
              onChange={(event) => handleChange('descricao', event.target.value)}
              rows="5"
              placeholder="Descreva o espaço, os diferenciais e a região."
              className="input-field"
            />
          </div>

          <div className="md:col-span-2">
            <label className="mb-2 block text-sm font-medium text-gray-700">Comodidades</label>
            <input
              type="text"
              value={form.comodidades}
              onChange={(event) => handleChange('comodidades', event.target.value)}
              placeholder="Wi-Fi, Piscina, Ar Condicionado"
              className="input-field"
            />
            <p className="mt-2 text-xs text-gray-500">Separe cada comodidade por vírgula.</p>
          </div>

          <div className="md:col-span-2 flex flex-col gap-3 pt-2 sm:flex-row sm:justify-end">
            <button type="button" onClick={() => navigate('/dashboard')} className="btn-ghost justify-center">
              Cancelar
            </button>
            <button type="submit" disabled={loading} className="btn-primary justify-center">
              {loading ? 'Salvando...' : 'Publicar imóvel'}
            </button>
          </div>
        </form>
      </div>
    </div>
  )
}
