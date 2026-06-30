import { createContext, useContext, useMemo, useState } from 'react'
import { api } from '../api/client'

const STORAGE_KEY = 'airbnbCloneUser'
const AuthContext = createContext(null)

export function AuthProvider({ children }) {
  const [user, setUser] = useState(() => {
    const saved = localStorage.getItem(STORAGE_KEY)
    return saved ? JSON.parse(saved) : null
  })

  const persistUser = (nextUser) => {
    setUser(nextUser)
    if (nextUser) {
      localStorage.setItem(STORAGE_KEY, JSON.stringify(nextUser))
    } else {
      localStorage.removeItem(STORAGE_KEY)
    }
  }

  const login = async (email, senha) => {
    const loggedUser = await api.login(email, senha)
    persistUser(loggedUser)
    return loggedUser
  }

  const logout = async () => {
    try {
      await api.logout()
    } finally {
      persistUser(null)
    }
  }

  const value = useMemo(() => {
    const tipo = user?.tipo?.toLowerCase?.() || ''
    const isGuest = tipo === 'hospede' || tipo === 'ambos'
    const isHost = tipo === 'anfitriao' || tipo === 'ambos'

    return {
      user,
      login,
      logout,
      isGuest,
      isHost,
    }
  }, [user])

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>
}

export function useAuth() {
  const context = useContext(AuthContext)

  if (!context) {
    throw new Error('useAuth deve ser usado dentro de AuthProvider')
  }

  return context
}
