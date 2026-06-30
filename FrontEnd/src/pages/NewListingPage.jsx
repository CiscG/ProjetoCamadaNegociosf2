import React, { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { api } from '../api/viagensApi'
import { useToast } from '../context/ToastContext'
import ToastContainer from '../components/ToastContainer'

const TIPOS_VIAGEM = [
  { value: 'NACIONAL', label: 'Viagem Nacional' },
  { value: 'INTERNACIONAL', label: 'Viagem Internacional' },
  { value: 'TURISMO_RELIGIOSO', label: 'Turismo Religioso' },
  { value: 'TURISMO_AVENTURA', label: 'Turismo de Aventura' },
  { value: 'TURISMO_PRAIA', label: 'Turismo de Praia' },
  { value: 'TURISMO_CULTURAL', label: 'Turismo Cultural' },
  { value: 'TURISMO_NEGOCIOS', label: 'Turismo de Negócios' },
]

export default function NewViagemPage() {
  const [form, setForm] = useState({
    destino: '',
    tipo: 'NACIONAL',
    dataPartida: '',
    dataRetorno: '',
    preco: '',
    vagas: '',
    descricao: '',
    ativa: true,
  })
  const [loading, setLoading] = useState(false)
  const { addToast } = useToast()
  const navigate = useNavigate()

  const handleChange = (e) => {
    const { name, value, type, checked } = e.target
    setForm(prev => ({
      ...prev,
      [name]: type === 'checkbox' ? checked : value,
    }))
  }

  const handleSubmit = async (e) => {
    e.preventDefault()
    setLoading(true)

    try {
      // Validações
      if (!form.destino.trim()) throw new Error('Destino é obrigatório')
      if (!form.dataPartida) throw new Error('Data de partida é obrigatória')
      if (!form.dataRetorno) throw new Error('Data de retorno é obrigatória')
      if (new Date(form.dataPartida) >= new Date(form.dataRetorno)) {
        throw new Error('Data de partida deve ser anterior à data de retorno')
      }
      if (!form.preco || isNaN(form.preco)) throw new Error('Preço inválido')
      if (!form.vagas || isNaN(form.vagas)) throw new Error('Vagas inválido')

      const viagemData = {
        destino: form.destino,
        tipo: form.tipo,
        dataPartida: form.dataPartida,
        dataRetorno: form.dataRetorno,
        preco: parseFloat(form.preco),
        vagas: parseInt(form.vagas),
        descricao: form.descricao,
        ativa: form.ativa,
      }

      await api.createViagem(viagemData)
      addToast('Viagem criada com sucesso!', 'success')
      navigate('/')
    } catch (error) {
      addToast(error.message || 'Erro ao criar viagem', 'error')
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="min-h-screen bg-gray-50 p-4 pt-8">
      <ToastContainer />
      <div className="max-w-2xl mx-auto bg-white rounded-lg shadow p-8">
        <h1 className="text-3xl font-bold mb-8">✈️ Criar Nova Viagem</h1>

        <form onSubmit={handleSubmit} className="space-y-6">
          <div className="grid grid-cols-2 gap-4">
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-2">
                Destino *
              </label>
              <input
                type="text"
                name="destino"
                value={form.destino}
                onChange={handleChange}
                placeholder="Ex: Rio de Janeiro"
                className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 outline-none"
                required
              />
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700 mb-2">
                Tipo de Viagem *
              </label>
              <select
                name="tipo"
                value={form.tipo}
                onChange={handleChange}
                className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 outline-none"
              >
                {TIPOS_VIAGEM.map(tipo => (
                  <option key={tipo.value} value={tipo.value}>
                    {tipo.label}
                  </option>
                ))}
              </select>
            </div>
          </div>

          <div className="grid grid-cols-2 gap-4">
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-2">
                Data de Partida *
              </label>
              <input
                type="date"
                name="dataPartida"
                value={form.dataPartida}
                onChange={handleChange}
                className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 outline-none"
                required
              />
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700 mb-2">
                Data de Retorno *
              </label>
              <input
                type="date"
                name="dataRetorno"
                value={form.dataRetorno}
                onChange={handleChange}
                className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 outline-none"
                required
              />
            </div>
          </div>

          <div className="grid grid-cols-2 gap-4">
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-2">
                Preço (R$) *
              </label>
              <input
                type="number"
                name="preco"
                value={form.preco}
                onChange={handleChange}
                placeholder="0.00"
                step="0.01"
                min="0"
                className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 outline-none"
                required
              />
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700 mb-2">
                Vagas Disponíveis *
              </label>
              <input
                type="number"
                name="vagas"
                value={form.vagas}
                onChange={handleChange}
                placeholder="0"
                min="0"
                className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 outline-none"
                required
              />
            </div>
          </div>

          <div>
            <label className="block text-sm font-medium text-gray-700 mb-2">
              Descrição
            </label>
            <textarea
              name="descricao"
              value={form.descricao}
              onChange={handleChange}
              placeholder="Descreva a viagem..."
              rows="4"
              className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 outline-none"
            />
          </div>

          <div className="flex items-center">
            <input
              type="checkbox"
              name="ativa"
              checked={form.ativa}
              onChange={handleChange}
              className="w-4 h-4 text-blue-600 rounded"
            />
            <label className="ml-2 text-sm font-medium text-gray-700">
              Viagem Ativa
            </label>
          </div>

          <div className="flex gap-4 pt-6">
            <button
              type="submit"
              disabled={loading}
              className="flex-1 bg-blue-600 text-white py-2 rounded-lg font-medium hover:bg-blue-700 transition disabled:opacity-50"
            >
              {loading ? 'Salvando...' : 'Criar Viagem'}
            </button>
            <button
              type="button"
              onClick={() => navigate('/')}
              className="flex-1 bg-gray-300 text-gray-800 py-2 rounded-lg font-medium hover:bg-gray-400 transition"
            >
              Cancelar
            </button>
          </div>
        </form>
      </div>
    </div>
  )
}
