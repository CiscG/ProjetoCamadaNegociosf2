import { BrowserRouter, Navigate, Route, Routes } from 'react-router-dom'
import { AuthProvider, useAuth } from './context/AuthContext'
import { ToastProvider } from './context/ToastContext'
import Header from './components/Header'
import Footer from './components/Footer'
import ToastContainer from './components/ToastContainer'
import LoginPage from './pages/LoginPage'
import HomePage from './pages/HomePage'
import NewListingPage from './pages/NewListingPage'
import DashboardPage from './pages/DashboardPage'
import PropertyPage from './pages/PropertyPage'

function ProtectedRoute({ children, hostsOnly = false }) {
  const { user, isHost } = useAuth()

  if (!user) return <Navigate to="/login" replace />
  if (hostsOnly && !isHost) return <Navigate to="/dashboard" replace />

  return children
}

function Shell({ children }) {
  const { user } = useAuth()

  return (
    <div className="min-h-screen bg-stone-50 text-gray-700">
      {user && <Header />}
      <main>{children}</main>
      <Footer />
    </div>
  )
}

function AppRoutes() {
  const { user } = useAuth()

  return (
    <Shell>
      <ToastContainer />
      <Routes>
        <Route path="/login" element={user ? <Navigate to="/" replace /> : <LoginPage />} />
        <Route path="/" element={<ProtectedRoute><HomePage /></ProtectedRoute>} />
        <Route path="/imoveis/:id" element={<ProtectedRoute><PropertyPage /></ProtectedRoute>} />
        <Route path="/novo-imovel" element={<ProtectedRoute hostsOnly><NewListingPage /></ProtectedRoute>} />
        <Route path="/dashboard" element={<ProtectedRoute><DashboardPage /></ProtectedRoute>} />
        <Route path="*" element={<Navigate to={user ? '/' : '/login'} replace />} />
      </Routes>
    </Shell>
  )
}

export default function App() {
  return (
    <BrowserRouter>
      <AuthProvider>
        <ToastProvider>
          <AppRoutes />
        </ToastProvider>
      </AuthProvider>
    </BrowserRouter>
  )
}
