import { Home, LayoutDashboard, LogOut, Plus } from 'lucide-react'
import { Link, useLocation, useNavigate } from 'react-router-dom'
import { useAuth } from '../context/AuthContext'

const ROLE_LABELS = {
  hospede: 'Hóspede',
  anfitriao: 'Anfitrião',
  ambos: 'Anfitrião e hóspede',
}

function NavLink({ to, icon: Icon, children, hidden = false }) {
  const location = useLocation()

  if (hidden) return null

  const active = location.pathname === to

  return (
    <Link
      to={to}
      className={[
        'inline-flex items-center gap-2 rounded-full px-4 py-2 text-sm font-medium transition',
        active ? 'bg-primary text-white shadow-sm' : 'text-gray-600 hover:bg-rose-50 hover:text-primary',
      ].join(' ')}
    >
      <Icon size={16} />
      {children}
    </Link>
  )
}

export default function Header() {
  const { user, logout, isHost } = useAuth()
  const navigate = useNavigate()

  const handleLogout = async () => {
    await logout()
    navigate('/login')
  }

  return (
    <header className="sticky top-0 z-40 border-b border-gray-200 bg-white/95 backdrop-blur-sm">
      <div className="mx-auto flex max-w-7xl flex-col gap-4 px-4 py-4 sm:flex-row sm:items-center sm:justify-between">
        <div className="flex items-center justify-between gap-4">
          <Link to="/" className="flex items-center gap-3">
            <span className="flex h-10 w-10 items-center justify-center rounded-full bg-primary text-lg text-white shadow-sm">A</span>
            <div>
              <p className="text-sm font-semibold uppercase tracking-[0.24em] text-primary">Airbnb Clone</p>
              <p className="text-xs text-gray-500">Aluguel de imóveis por temporada</p>
            </div>
          </Link>
          <span className="rounded-full bg-rose-50 px-3 py-1 text-xs font-semibold text-primary sm:hidden">
            {ROLE_LABELS[user?.tipo] || 'Usuário'}
          </span>
        </div>

        <div className="flex flex-col gap-3 sm:flex-row sm:items-center">
          <nav className="flex flex-wrap gap-2">
            <NavLink to="/" icon={Home}>Início</NavLink>
            <NavLink to="/novo-imovel" icon={Plus} hidden={!isHost}>Novo imóvel</NavLink>
            <NavLink to="/dashboard" icon={LayoutDashboard}>Dashboard</NavLink>
          </nav>

          <div className="flex items-center justify-between gap-3 rounded-full border border-gray-200 px-3 py-2">
            <div className="hidden text-right sm:block">
              <p className="text-sm font-semibold text-gray-900">{user?.nome}</p>
              <p className="text-xs text-gray-500">{ROLE_LABELS[user?.tipo] || 'Usuário'}</p>
            </div>
            <button
              type="button"
              onClick={handleLogout}
              className="inline-flex items-center gap-2 rounded-full bg-gray-900 px-4 py-2 text-sm font-medium text-white transition hover:bg-gray-700"
            >
              <LogOut size={16} />
              Sair
            </button>
          </div>
        </div>
      </div>
    </header>
  )
}
