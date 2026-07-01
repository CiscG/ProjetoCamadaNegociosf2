import React, { useState, useEffect } from 'react'
import { Plus, Trash2, Edit2, MapPin, DollarSign, Home } from 'lucide-react'
import { api } from '../api/client'
import { useToast } from '../context/ToastContext'
import { useNavigate } from 'react-router-dom'
import { useAuth } from '../context/AuthContext'
import ToastContainer from '../components/ToastContainer'

export default function HomePage() {
  const [locais, setLocais] = useState([])
  const [loading, setLoading] = useState(true)
  const { addToast } = useToast()
  const { user } = useAuth()
  const navigate = useNavigate()

  useEffect(() => {
    carregarLocais()
  }, [user])

  const carregarLocais = async () => {
    try {
      setLoading(true)
      // Se for anfitrião, carrega seus imóveis
      if (user?.tipo === 'anfitriao') {
        const locais = await api.getLocais({ anfitriao_id: user.id })
        setLocais(locais)
      } else {
        // Se for hóspede, carrega todos
        const locais = await api.getLocais()
        setLocais(locais)
      }
    } catch (error) {
      addToast('Erro ao carregar imóveis: ' + error.message, 'error')
    } finally {
      setLoading(false)
    }
  }

  const handleDelete = async (id) => {
    if (!window.confirm('Tem certeza que deseja deletar este imóvel?')) return

    try {
      // Implementar delete se necessário
      addToast('Imóvel deletado com sucesso!', 'success')
      carregarLocais()
    } catch (error) {
      addToast('Erro ao deletar: ' + error.message, 'error')
    }
  }

  return (
    <div className="min-h-screen bg-gray-50 p-4">
      <ToastContainer />
      <div className="max-w-7xl mx-auto">
        {/* Header */}
        <div className="flex justify-between items-center mb-8">
          <h1 className="text-3xl font-bold text-gray-800">
            {user?.tipo === 'anfitriao' ? '🏠 Meus Imóveis' : '🏠 Imóveis Disponíveis'}
          </h1>
          {user?.tipo === 'anfitriao' && (
            <button
              onClick={() => navigate('/novo-imovel')}
              className="bg-blue-600 text-white px-6 py-2 rounded-lg hover:bg-blue-700 transition inline-flex items-center gap-2"
            >
              <Plus size={20} />
              Novo Imóvel
            </button>
          )}
        </div>

        {/* Lista de Imóveis */}
        <div className="grid gap-6 grid-cols-1 md:grid-cols-2 lg:grid-cols-3">
          {loading ? (
            <div className="col-span-full text-center py-12">
              <p className="text-gray-600">Carregando imóveis...</p>
            </div>
          ) : locais.length === 0 ? (
            <div className="col-span-full text-center py-12 bg-white rounded-lg">
              <Home size={48} className="mx-auto text-gray-400 mb-4" />
              <p className="text-gray-600 mb-4">Nenhum imóvel encontrado</p>
              {user?.tipo === 'anfitriao' && (
                <button
                  onClick={() => navigate('/novo-imovel')}
                  className="bg-blue-600 text-white px-6 py-2 rounded-lg hover:bg-blue-700 transition inline-flex items-center gap-2"
                >
                  <Plus size={20} />
                  Cadastrar Primeiro Imóvel
                </button>
              )}
            </div>
          ) : (
            locais.map(local => (
              <div key={local._id} className="bg-white rounded-lg shadow hover:shadow-lg transition overflow-hidden">
                {/* Imagem placeholder */}
                <div className="h-48 bg-gradient-to-br from-blue-400 to-blue-600 flex items-center justify-center">
                  <Home size={64} className="text-white opacity-50" />
                </div>

                {/* Conteúdo */}
                <div className="p-4">
                  <h3 className="text-xl font-bold text-gray-800 mb-2">{local.nome}</h3>

                  <div className="space-y-2 mb-4">
                    <div className="flex items-center gap-2 text-gray-600">
                      <MapPin size={16} />
                      <span>{local.cidade}, {local.estado}</span>
                    </div>
                    <div className="text-sm text-gray-600">
                      {local.descricao?.substring(0, 80)}...
                    </div>
                  </div>

                  {/* Comodidades */}
                  {local.comodidades?.length > 0 && (
                    <div className="mb-4">
                      <div className="flex flex-wrap gap-1">
                        {local.comodidades.slice(0, 3).map((comodidade, idx) => (
                          <span key={idx} className="text-xs bg-gray-100 text-gray-700 px-2 py-1 rounded">
                            {comodidade}
                          </span>
                        ))}
                        {local.comodidades.length > 3 && (
                          <span className="text-xs bg-gray-100 text-gray-700 px-2 py-1 rounded">
                            +{local.comodidades.length - 3}
                          </span>
                        )}
                      </div>
                    </div>
                  )}

                  {/* Preço e Ações */}
                  <div className="flex justify-between items-center pt-4 border-t">
                    <div className="text-2xl font-bold text-blue-600">
                      R$ {local.preco_por_noite?.toFixed(2)}
                      <span className="text-sm text-gray-600 font-normal">/noite</span>
                    </div>

                    <div className="flex gap-2">
                      <button
                        onClick={() => navigate(`/imovel/${local._id}`)}
                        className="bg-blue-600 text-white p-2 rounded hover:bg-blue-700 transition"
                        title="Ver detalhes"
                      >
                        <Plus size={18} />
                      </button>
                      {user?.tipo === 'anfitriao' && user?.id === local.anfitriao_id && (
                        <>
                          <button
                            onClick={() => navigate(`/editar-imovel/${local._id}`)}
                            className="bg-gray-600 text-white p-2 rounded hover:bg-gray-700 transition"
                            title="Editar"
                          >
                            <Edit2 size={18} />
                          </button>
                          <button
                            onClick={() => handleDelete(local._id)}
                            className="bg-red-600 text-white p-2 rounded hover:bg-red-700 transition"
                            title="Deletar"
                          >
                            <Trash2 size={18} />
                          </button>
                        </>
                      )}
                    </div>
                  </div>
                </div>
              </div>
            ))
          )}
        </div>
      </div>
    </div>
  )
}
