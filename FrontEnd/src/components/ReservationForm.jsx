import { useMemo, useState } from 'react'
import { Calendar } from 'lucide-react'
import LoadingSpinner from './LoadingSpinner'

export default function ReservationForm({ property, canReserve, onReserve, loading }) {
  const [desde, setDesde] = useState('')
  const [ate, setAte] = useState('')
  const today = new Date().toISOString().slice(0, 10)

  const nights = useMemo(() => {
    if (!desde || !ate) return 0
    const diff = Math.ceil((new Date(ate) - new Date(desde)) / (1000 * 60 * 60 * 24))
    return Number.isNaN(diff) ? 0 : Math.max(0, diff)
  }, [ate, desde])

  const total = nights * (property?.preco_por_noite || 0)

  const handleSubmit = async (event) => {
    event.preventDefault()

    const reserved = await onReserve({ desde, ate, noites: nights, total })

    if (reserved) {
      setDesde('')
      setAte('')
    }
  }

  if (!canReserve) {
    return (
      <div className="rounded-2xl border border-gray-100 bg-white p-6 shadow-lg">
        <h2 className="mb-3 flex items-center gap-2 text-lg font-bold text-gray-900">
          <Calendar size={18} className="text-primary" /> Reservas
        </h2>
        <p className="text-sm text-gray-500">Apenas hóspedes podem reservar este imóvel.</p>
      </div>
    )
  }

  return (
    <div className="rounded-2xl border border-gray-100 bg-white p-6 shadow-lg">
      <h2 className="mb-5 flex items-center gap-2 text-lg font-bold text-gray-900">
        <Calendar size={18} className="text-primary" /> Fazer reserva
      </h2>

      <form onSubmit={handleSubmit} className="space-y-4">
        <div>
          <label className="mb-1.5 block text-xs font-semibold uppercase tracking-wide text-gray-600">
            Check-in
          </label>
          <input
            type="date"
            value={desde}
            min={today}
            onChange={(event) => setDesde(event.target.value)}
            className="input-field"
            required
          />
        </div>

        <div>
          <label className="mb-1.5 block text-xs font-semibold uppercase tracking-wide text-gray-600">
            Check-out
          </label>
          <input
            type="date"
            value={ate}
            min={desde || today}
            onChange={(event) => setAte(event.target.value)}
            className="input-field"
            required
          />
        </div>

        {nights > 0 && (
          <div className="space-y-2 rounded-xl bg-gray-50 p-4 text-sm">
            <div className="flex justify-between text-gray-600">
              <span>R$ {property.preco_por_noite?.toLocaleString('pt-BR')} × {nights} noite{nights > 1 ? 's' : ''}</span>
              <span>R$ {total.toLocaleString('pt-BR', { minimumFractionDigits: 2 })}</span>
            </div>
            <div className="flex justify-between border-t border-gray-200 pt-2 font-bold text-gray-900">
              <span>Total</span>
              <span>R$ {total.toLocaleString('pt-BR', { minimumFractionDigits: 2 })}</span>
            </div>
          </div>
        )}

        <button type="submit" disabled={loading} className="btn-primary w-full py-3.5">
          {loading ? <LoadingSpinner size="sm" className="border-white border-t-transparent" /> : 'Reservar agora'}
        </button>
      </form>
    </div>
  )
}
