import React, { useState, useEffect } from 'react'
import { Plus, Trash2, Edit2, Mail, Phone } from 'lucide-react'
import { api } from '../api/viagensApi'
import { useToast } from '../context/ToastContext'
import ToastContainer from '../components/ToastContainer'

export default function ClientesPage() {
  const [clientes, setClientes] = useState([])
  const [loading, setLoading] = useState(true)
  const [showForm, setShowForm] = useState(false)
  const [form, setForm] = useState({
    nome: '',
    email: '',
    telefone: '',
    cpf: '',
    idade: '',
    renda: '',
  })
  const { addToast } = useToast()

  useEffect(() => {
    carregarClientes()
  }, [])

  const carregarClientes = async () => {
    try {
      setLoading(true)
      const response = await api.getClientes()
      setClientes(response.clientes || [])
    } catch (error) {
      addToast('Erro ao carregar clientes: ' + error.message, 'error')
    } finally {
      setLoading(false)
    }
  }

  const handleChange = (e) => {
    const { name, value } = e.target
    setForm(prev => ({
      ...prev,
      [name]: value,
    }))
  }

  const handleSubmit = async (e) => {
    e.preventDefault()

    try {
      if (!form.nome.trim()) throw new Error('Nome é obrigatório')
      if (!form.email.trim()) throw new Error('Email é obrigatório')
      if (!form.cpf.trim()) throw new Error('CPF é obrigatório')

      const clienteData = {
        nome: form.nome,
        email: form.email,
        telefone: form.telefone,
        cpf: form.cpf,
        idade: parseInt(form.idade) || 0,
        renda: parseFloat(form.renda) || 0,
        ativo: true,
      }

      await api.createCliente(clienteData)
      addToast('Cliente criado com sucesso!', 'success')
      setForm({ nome: '', email: '', telefone: '', cpf: '', idade: '', renda: '' })
      setShowForm(false)
      carregarClientes()
    } catch (error) {
      addToast(error.message || 'Erro ao criar cliente', 'error')
    }
  }

  const handleDelete = async (id) => {
    if (!window.confirm('Tem certeza que deseja deletar este cliente?')) return

    try {
      await api.deleteCliente(id)
      addToast('Cliente deletado com sucesso!', 'success')
      carregarClientes()
    } catch (error) {
      addToast('Erro ao deletar: ' + error.message, 'error')
    }
  }

  return (
    <div className="min-h-screen bg-gray-50 p-4 pt-8">
      <ToastContainer />
      <div className="max-w-6xl mx-auto">
        <div className="flex justify-between items-center mb-8">
          <h1 className="text-3xl font-bold">👥 Clientes</h1>
          <button
            onClick={() => setShowForm(!showForm)}
            className="bg-blue-600 text-white px-4 py-2 rounded-lg hover:bg-blue-700 transition inline-flex items-center gap-2"
          >
            <Plus size={20} />
            Novo Cliente
          </button>
        </div>

        {/* Formulário */}
        {showForm && (
          <div className="bg-white rounded-lg shadow p-6 mb-8">
            <h2 className="text-xl font-bold mb-4">Criar Novo Cliente</h2>
            <form onSubmit={handleSubmit} className="grid grid-cols-2 gap-4">
              <input
                type="text"
                name="nome"
                value={form.nome}
                onChange={handleChange}
                placeholder="Nome"
                className="col-span-2 px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 outline-none"
              />
              <input
                type="email"
                name="email"
                value={form.email}
                onChange={handleChange}
                placeholder="Email"
                className="px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 outline-none"
              />
              <input
                type="tel"
                name="telefone"
                value={form.telefone}
                onChange={handleChange}
                placeholder="Telefone"
                className="px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 outline-none"
              />
              <input
                type="text"
                name="cpf"
                value={form.cpf}
                onChange={handleChange}
                placeholder="CPF"
                className="px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 outline-none"
              />
              <input
                type="number"
                name="idade"
                value={form.idade}
                onChange={handleChange}
                placeholder="Idade"
                className="px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 outline-none"
              />
              <input
                type="number"
                name="renda"
                value={form.renda}
                onChange={handleChange}
                placeholder="Renda"
                step="0.01"
                className="px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 outline-none"
              />
              <button
                type="submit"
                className="col-span-2 bg-green-600 text-white py-2 rounded-lg hover:bg-green-700 transition"
              >
                Salvar Cliente
              </button>
            </form>
          </div>
        )}

        {/* Lista de Clientes */}
        <div className="grid gap-4">
          {loading ? (
            <div className="text-center py-12 bg-white rounded-lg">
              <p className="text-gray-600">Carregando clientes...</p>
            </div>
          ) : clientes.length === 0 ? (
            <div className="text-center py-12 bg-white rounded-lg">
              <p className="text-gray-600">Nenhum cliente cadastrado</p>
            </div>
          ) : (
            clientes.map(cliente => (
              <div key={cliente.id} className="bg-white rounded-lg shadow p-4 flex justify-between items-center">
                <div className="flex-1">
                  <h3 className="font-bold text-lg text-gray-800">{cliente.nome}</h3>
                  <div className="flex gap-4 text-sm text-gray-600 mt-2">
                    <div className="flex items-center gap-1">
                      <Mail size={16} />
                      {cliente.email}
                    </div>
                    <div className="flex items-center gap-1">
                      <Phone size={16} />
                      {cliente.telefone}
                    </div>
                    <span>CPF: {cliente.cpf}</span>
                    <span>Idade: {cliente.idade}</span>
                    <span>Renda: R$ {cliente.renda?.toFixed(2)}</span>
                  </div>
                </div>
                <button
                  onClick={() => handleDelete(cliente.id)}
                  className="bg-red-600 text-white p-2 rounded hover:bg-red-700 transition"
                >
                  <Trash2 size={18} />
                </button>
              </div>
            ))
          )}
        </div>
      </div>
    </div>
  )
}
