import React from 'react'
import { LogOut, Home, Plus, User } from 'lucide-react'
import { useAuth } from '../context/AuthContext'
import { useNavigate } from 'react-router-dom'

export default function Header() {
  const { user, logout } = useAuth()
  const navigate = useNavigate()

  const handleLogout = () => {
    logout()
    navigate('/login')
  }

  return (
    <header className="bg-gradient-to-r from-blue-600 to-blue-800 text-white shadow-lg">
      <div className="max-w-7xl mx-auto px-4 py-4">
        <div className="flex items-center justify-between">
          <div className="flex items-center gap-8">
            <h1 className="text-2xl font-bold cursor-pointer" onClick={() => navigate('/')}>
              ✈️ Agência de Viagens
            </h1>
            <nav className="flex gap-6">
              <button
                onClick={() => navigate('/')}
                className="flex items-center gap-2 hover:text-blue-200 transition"
              >
                <Home size={20} />
                Viagens
              </button>
              <button
                onClick={() => navigate('/novo-viagem')}
                className="flex items-center gap-2 hover:text-blue-200 transition"
              >
                <Plus size={20} />
                Nova Viagem
              </button>
              <button
                onClick={() => navigate('/clientes')}
                className="flex items-center gap-2 hover:text-blue-200 transition"
              >
                <User size={20} />
                Clientes
              </button>
            </nav>
          </div>
          <div className="flex items-center gap-4">
            <span className="text-sm">{user?.nome || 'Usuário'}</span>
            <button
              onClick={handleLogout}
              className="flex items-center gap-2 bg-red-600 px-4 py-2 rounded hover:bg-red-700 transition"
            >
              <LogOut size={18} />
              Sair
            </button>
          </div>
        </div>
      </div>
    </header>
  )
}
