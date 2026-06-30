import { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { useAuth } from '../context/AuthContext'
import { useToast } from '../context/ToastContext'

const DEMO_USERS = [
  { email: 'carlos@email.com', tipo: 'Anfitrião' },
  { email: 'maria@email.com', tipo: 'Hóspede' },
  { email: 'ana@email.com', tipo: 'Ambos' },
]

export default function LoginPage() {
  const [form, setForm] = useState({ email: '', senha: '123456' })
  const [loading, setLoading] = useState(false)
  const { login } = useAuth()
  const { addToast } = useToast()
  const navigate = useNavigate()

  const handleChange = (field, value) => {
    setForm((current) => ({ ...current, [field]: value }))
  }

  const fillDemoUser = (email) => {
    setForm({ email, senha: '123456' })
  }

  const handleSubmit = async (event) => {
    event.preventDefault()
    setLoading(true)

    try {
      await login(form.email, form.senha)
      addToast('Login realizado com sucesso!', 'success')
      navigate('/')
    } catch (error) {
      addToast(error.message || 'Não foi possível entrar.', 'error')
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="flex min-h-[calc(100vh-73px)] items-center justify-center bg-gradient-to-br from-rose-50 via-white to-stone-100 px-4 py-12">
      <div className="grid w-full max-w-6xl overflow-hidden rounded-[2rem] border border-rose-100 bg-white shadow-2xl lg:grid-cols-[1.05fr_0.95fr]">
        <section className="flex flex-col justify-between bg-gray-900 px-8 py-10 text-white sm:px-12">
          <div>
            <span className="inline-flex rounded-full bg-white/10 px-4 py-1 text-xs font-semibold uppercase tracking-[0.3em] text-rose-200">
              Airbnb Clone
            </span>
            <h1 className="mt-6 text-4xl font-bold leading-tight sm:text-5xl">
              Reserve imóveis incríveis com um fluxo simples e moderno.
            </h1>
            <p className="mt-4 max-w-lg text-sm leading-7 text-gray-300 sm:text-base">
              Faça login como hóspede, anfitrião ou ambos para explorar imóveis, cadastrar novas hospedagens e acompanhar reservas em um só lugar.
            </p>
          </div>

          <div className="mt-8 grid gap-3 sm:grid-cols-3">
            {DEMO_USERS.map((user) => (
              <button
                key={user.email}
                type="button"
                onClick={() => fillDemoUser(user.email)}
                className="rounded-2xl border border-white/10 bg-white/5 p-4 text-left transition hover:border-rose-300/60 hover:bg-white/10"
              >
                <p className="text-sm font-semibold text-white">{user.tipo}</p>
                <p className="mt-2 text-xs text-gray-300">{user.email}</p>
              </button>
            ))}
          </div>
        </section>

        <section className="px-8 py-10 sm:px-12">
          <div className="mx-auto max-w-md">
            <h2 className="text-3xl font-bold text-gray-900">Entrar</h2>
            <p className="mt-2 text-sm text-gray-500">
              Use um dos logins de teste e a senha padrão <span className="font-semibold text-primary">123456</span>.
            </p>

            <form onSubmit={handleSubmit} className="mt-8 space-y-5">
              <div>
                <label className="mb-2 block text-sm font-medium text-gray-700">Email</label>
                <input
                  type="email"
                  value={form.email}
                  onChange={(event) => handleChange('email', event.target.value)}
                  placeholder="seu@email.com"
                  className="input-field"
                  required
                />
              </div>

              <div>
                <label className="mb-2 block text-sm font-medium text-gray-700">Senha</label>
                <input
                  type="password"
                  value={form.senha}
                  onChange={(event) => handleChange('senha', event.target.value)}
                  placeholder="123456"
                  className="input-field"
                  required
                  minLength={6}
                />
              </div>

              <button type="submit" disabled={loading} className="btn-primary w-full justify-center py-3.5">
                {loading ? 'Entrando...' : 'Acessar plataforma'}
              </button>
            </form>
          </div>
        </section>
      </div>
    </div>
  )
}
