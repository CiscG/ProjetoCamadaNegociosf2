import { useCallback, useEffect, useState } from 'react'
import { AlertCircle, ArrowLeft, MapPin, Wifi, Wind, Car, Waves } from 'lucide-react'
import { useNavigate, useParams } from 'react-router-dom'
import { api } from '../api/client'
import ReservationForm from '../components/ReservationForm'
import { PageLoader } from '../components/LoadingSpinner'
import { useAuth } from '../context/AuthContext'
import { useToast } from '../context/ToastContext'

const PROPERTY_IMAGES = {
  '75ef00000000000000000001': 'https://images.unsplash.com/photo-1560448204-e02f11c3d0e2?w=1200&q=80',
  '75ef00000000000000000002': 'https://images.unsplash.com/photo-1449844908441-895c6697abe2?w=1200&q=80',
  '75ef00000000000000000003': 'https://images.unsplash.com/photo-1502672260266-1c1ef2d93688?w=1200&q=80',
}

const AMENITY_ICONS = {
  wifi: <Wifi size={16} />,
  'ar-condicionado': <Wind size={16} />,
  'ar condicionado': <Wind size={16} />,
  estacionamento: <Car size={16} />,
  piscina: <Waves size={16} />,
}

function rangesOverlap(startA, endA, startB, endB) {
  return new Date(startA) < new Date(endB) && new Date(endA) > new Date(startB)
}

export default function PropertyPage() {
  const { id } = useParams()
  const navigate = useNavigate()
  const { user, isGuest } = useAuth()
  const { addToast } = useToast()
  const [property, setProperty] = useState(null)
  const [occupiedDates, setOccupiedDates] = useState([])
  const [loading, setLoading] = useState(true)
  const [booking, setBooking] = useState(false)

  const loadProperty = useCallback(async () => {
    setLoading(true)

    try {
      const [locais, ocupacao] = await Promise.all([api.getLocais(), api.getOcupacao(id)])
      const selected = locais.find((local) => local.id === id)

      if (!selected) {
        throw new Error('Imóvel não encontrado.')
      }

      setProperty(selected)
      setOccupiedDates(ocupacao)
    } catch (error) {
      addToast(error.message || 'Erro ao carregar imóvel.', 'error')
      navigate('/')
    } finally {
      setLoading(false)
    }
  }, [addToast, id, navigate])

  useEffect(() => {
    loadProperty()
  }, [loadProperty])

  const handleReserve = async ({ desde, ate, noites }) => {
    if (!desde || !ate) {
      addToast('Selecione as datas da reserva.', 'error')
      return false
    }

    if (noites <= 0) {
      addToast('O check-out deve ocorrer após o check-in.', 'error')
      return false
    }

    if (occupiedDates.some((range) => rangesOverlap(desde, ate, range.desde, range.ate))) {
      addToast('As datas selecionadas já estão ocupadas.', 'error')
      return false
    }

    setBooking(true)

    try {
      await api.createReserva({
        local_id: id,
        hospede_id: user.id,
        desde,
        ate,
      })

      addToast('Reserva realizada com sucesso!', 'success')
      setOccupiedDates(await api.getOcupacao(id))
      return true
    } catch (error) {
      addToast(error.message || 'Erro ao realizar reserva.', 'error')
      return false
    } finally {
      setBooking(false)
    }
  }

  if (loading) {
    return <PageLoader />
  }

  if (!property) {
    return null
  }

  const imageSrc = PROPERTY_IMAGES[id] || `https://picsum.photos/seed/${id}/1200/700`

  return (
    <div className="bg-stone-50">
      <div className="relative h-72 overflow-hidden bg-gray-200 sm:h-[26rem]">
        <img src={imageSrc} alt={property.nome} className="h-full w-full object-cover" />
        <div className="absolute inset-0 bg-gradient-to-t from-black/60 to-transparent" />
        <button
          type="button"
          onClick={() => navigate(-1)}
          className="absolute left-4 top-4 rounded-full bg-white/90 p-2 shadow-md transition hover:bg-white"
        >
          <ArrowLeft size={18} className="text-gray-700" />
        </button>
        <div className="absolute bottom-5 left-4 right-4 sm:left-8 sm:right-8">
          <h1 className="text-3xl font-bold text-white sm:text-4xl">{property.nome}</h1>
          <p className="mt-2 flex items-center gap-1.5 text-sm text-white/90">
            <MapPin size={14} /> {property.cidade}, {property.estado}
          </p>
        </div>
      </div>

      <div className="mx-auto grid max-w-7xl gap-8 px-4 py-8 sm:px-6 lg:grid-cols-[minmax(0,1fr)_360px] lg:px-8">
        <section className="space-y-6">
          <div className="rounded-[2rem] border border-gray-200 bg-white p-6 shadow-sm sm:p-8">
            <div className="flex flex-wrap items-end justify-between gap-4">
              <div>
                <p className="text-sm font-semibold uppercase tracking-[0.3em] text-primary">Hospedagem</p>
                <h2 className="mt-3 text-2xl font-bold text-gray-900">R$ {property.preco_por_noite?.toLocaleString('pt-BR')}/noite</h2>
              </div>
              <span className="rounded-full bg-rose-50 px-4 py-2 text-sm font-semibold text-primary">
                Avaliação {property.avaliacao?.toFixed(1)}
              </span>
            </div>
            <p className="mt-5 text-sm leading-7 text-gray-600">{property.descricao}</p>
          </div>

          {property.comodidades?.length > 0 && (
            <div className="rounded-[2rem] border border-gray-200 bg-white p-6 shadow-sm sm:p-8">
              <h3 className="text-xl font-bold text-gray-900">Comodidades</h3>
              <div className="mt-5 grid gap-3 sm:grid-cols-2 lg:grid-cols-3">
                {property.comodidades.map((comodidade) => (
                  <div key={comodidade} className="flex items-center gap-2.5 rounded-2xl bg-stone-50 px-4 py-3 text-sm text-gray-600">
                    <span className="text-primary">{AMENITY_ICONS[comodidade.toLowerCase()] || '✓'}</span>
                    {comodidade}
                  </div>
                ))}
              </div>
            </div>
          )}

          <div className="rounded-[2rem] border border-gray-200 bg-white p-6 shadow-sm sm:p-8">
            <div className="flex items-start gap-3">
              <AlertCircle size={18} className="mt-0.5 text-primary" />
              <div>
                <h3 className="text-xl font-bold text-gray-900">Períodos ocupados</h3>
                <p className="mt-2 text-sm text-gray-500">Use essas datas para planejar a sua reserva.</p>
              </div>
            </div>

            <div className="mt-5 space-y-3">
              {occupiedDates.length === 0 ? (
                <p className="text-sm text-gray-500">Nenhuma data bloqueada no momento.</p>
              ) : (
                occupiedDates.map((period, index) => (
                  <div key={`${period.desde}-${period.ate}-${index}`} className="rounded-2xl bg-rose-50 px-4 py-3 text-sm text-rose-700">
                    {new Date(period.desde).toLocaleDateString('pt-BR')} até {new Date(period.ate).toLocaleDateString('pt-BR')}
                  </div>
                ))
              )}
            </div>
          </div>
        </section>

        <aside className="lg:sticky lg:top-24 lg:self-start">
          <ReservationForm
            property={property}
            canReserve={isGuest}
            onReserve={handleReserve}
            loading={booking}
          />
        </aside>
      </div>
    </div>
  )
}
