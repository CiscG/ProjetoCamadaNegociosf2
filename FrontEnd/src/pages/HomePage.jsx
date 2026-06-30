import React, { useState, useEffect } from 'react'
import { Plus, Trash2, Edit2, MapPin, Calendar, DollarSign } from 'lucide-react'
import { api } from '../api/viagensApi'
import { useToast } from '../context/ToastContext'
import { useNavigate } from 'react-router-dom'
import ToastContainer from '../components/ToastContainer'

export default function HomePage() {
  const [viagens, setViagens] = useState([])
  const [loading, setLoading] = useState(true)
  const [searchDestino, setSearchDestino] = useState('')
  const { addToast } = useToast()
  const navigate = useNavigate()

  useEffect(() => {
    carregarViagens()
  }, [])

  const carregarViagens = async () => {
    try {
      setLoading(true)
      const response = await api.getViagens()
      setViagens(response.viagens || [])
    } catch (error) {
      addToast('Erro ao carregar viagens: ' + error.message, 'error')
    } finally {
      setLoading(false)
    }
  }

  const buscarPorDestino = async () => {
    if (!searchDestino.trim()) {
      carregarViagens()
      return
    }

    try {
      const response = await api.buscarViagensPorDestino(searchDestino)
      setViagens(response.viagens || [])
    } catch (error) {
      addToast('Erro ao buscar: ' + error.message, 'error')
    }
  }

  const handleDelete = async (id) => {
    if (!window.confirm('Tem certeza que deseja deletar esta viagem?')) return

    try {
      await api.deleteViagem(id)
      addToast('Viagem deletada com sucesso!', 'success')
      carregarViagens()
    } catch (error) {
      addToast('Erro ao deletar: ' + error.message, 'error')
    }
  }

  const formatarData = (data) => {
    return new Date(data).toLocaleDateString('pt-BR')
  }

  return (
    <div className="min-h-screen bg-gray-50 p-4">
      <ToastContainer />
      <div className="max-w-7xl mx-auto">
        {/* Busca e Filtros */}
        <div className="bg-white rounded-lg shadow p-6 mb-8">
          <h2 className="text-2xl font-bold mb-4">🔍 Buscar Viagens</h2>
          <div className="flex gap-2">
            <input
              type="text"
              value={searchDestino}
              onChange={(e) => setSearchDestino(e.target.value)}
              placeholder="Digite o destino..."
              className="flex-1 px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 outline-none"
              onKeyPress={(e) => e.key === 'Enter' && buscarPorDestino()}
            />
            <button
              onClick={buscarPorDestino}
              className="bg-blue-600 text-white px-6 py-2 rounded-lg hover:bg-blue-700 transition"
            >
              Buscar
            </button>
            <button
              onClick={carregarViagens}
              className="bg-gray-600 text-white px-6 py-2 rounded-lg hover:bg-gray-700 transition"
            >
              Limpar
            </button>
          </div>
        </div>

        {/* Lista de Viagens */}
        <div className="grid gap-6">
          {loading ? (
            <div className="text-center py-12">
              <p className="text-gray-600">Carregando viagens...</p>
            </div>
          ) : viagens.length === 0 ? (
            <div className="text-center py-12 bg-white rounded-lg">
              <p className="text-gray-600 mb-4">Nenhuma viagem encontrada</p>
              <button
                onClick={() => navigate('/novo-viagem')}
                className="bg-blue-600 text-white px-6 py-2 rounded-lg hover:bg-blue-700 transition inline-flex items-center gap-2"
              >
                <Plus size={20} />
                Criar Primeira Viagem
              </button>
            </div>
          ) : (
            viagens.map(viagem => (
              <div key={viagem.id} className="bg-white rounded-lg shadow hover:shadow-lg transition p-6">
                <div className="flex justify-between items-start mb-4">
                  <div className="flex-1">
                    <h3 className="text-2xl font-bold text-gray-800 mb-2">{viagem.destino}</h3>
                    <div className="flex flex-wrap gap-4 text-sm text-gray-600">
                      <div className="flex items-center gap-1">
                        <MapPin size={16} />
                        <span>Tipo: {viagem.tipo?.descricao || 'N/A'}</span>
                      </div>
                      <div className="flex items-center gap-1">
                        <Calendar size={16} />
                        <span>{formatarData(viagem.dataPartida)} a {formatarData(viagem.dataRetorno)}</span>
                      </div>
                      <div className="flex items-center gap-1">
                        <DollarSign size={16} />
                        <span>R$ {viagem.preco?.toFixed(2)}</span>
                      </div>
                    </div>
                  </div>
                  <div className="flex gap-2">
                    <button
                      onClick={() => navigate(`/viagem/${viagem.id}`)}
                      className="bg-blue-600 text-white p-2 rounded hover:bg-blue-700 transition"
                      title="Editar"
                    >
                      <Edit2 size={18} />
                    </button>
                    <button
                      onClick={() => handleDelete(viagem.id)}
                      className="bg-red-600 text-white p-2 rounded hover:bg-red-700 transition"
                      title="Deletar"
                    >
                      <Trash2 size={18} />
                    </button>
                  </div>
                </div>
                <p className="text-gray-700 mb-3">{viagem.descricao}</p>
                <div className="flex justify-between items-center">
                  <span className="text-sm text-gray-600">Vagas disponíveis: {viagem.vagas}</span>
                  <span className={`px-3 py-1 rounded-full text-sm font-medium ${
                    viagem.ativa ? 'bg-green-100 text-green-800' : 'bg-red-100 text-red-800'
                  }`}>
                    {viagem.ativa ? '✓ Ativa' : '✕ Inativa'}
                  </span>
                </div>
              </div>
            ))
          )}
        </div>
      </div>
    </div>
  )
}
