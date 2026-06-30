import { useEffect, useState } from 'react'
import { CalendarRange, Home, LayoutDashboard, UserRound } from 'lucide-react'
import { api } from '../api/client'
import ReservationCard from '../components/ReservationCard'
import { PageLoader } from '../components/LoadingSpinner'
import { useAuth } from '../context/AuthContext'
import { useToast } from '../context/ToastContext'

function OccupancyList({ periods }) {
  if (!periods.length) {
    return <p className="text-sm text-gray-500">Nenhum período ocupado até o momento.</p>
  }

  return (
    <div className="space-y-2">
      {periods.map((period, index) => (
        <div key={`${period.desde}-${period.ate}-${index}`} className="rounded-xl bg-rose-50 px-4 py-3 text-sm text-rose-700">
          {new Date(period.desde).toLocaleDateString('pt-BR')} até {new Date(period.ate).toLocaleDateString('pt-BR')}
        </div>
      ))}
    </div>
  )
}

export default function DashboardPage() {
  const { user, isGuest, isHost } = useAuth()
  const { addToast } = useToast()
  const [loading, setLoading] = useState(true)
  const [reservas, setReservas] = useState([])
  const [imoveis, setImoveis] = useState([])
  const [ocupacoes, setOcupacoes] = useState({})

  useEffect(() => {
    let cancelled = false

    async function bootstrap() {
      setLoading(true)

      try {
        const [guestReservations, hostListings] = await Promise.all([
          isGuest ? api.getReservas({ hospede_id: user.id }) : Promise.resolve([]),
          isHost ? api.getLocais({ anfitriao_id: user.id }) : Promise.resolve([]),
        ])

        const occupancyEntries = await Promise.all(
          hostListings.map(async (listing) => [listing.id, await api.getOcupacao(listing.id).catch(() => [])])
        )

        if (!cancelled) {
          setReservas(guestReservations)
          setImoveis(hostListings)
          setOcupacoes(Object.fromEntries(occupancyEntries))
        }
      } catch (error) {
        if (!cancelled) {
          addToast(error.message || 'Erro ao carregar dashboard.', 'error')
        }
      } finally {
        if (!cancelled) {
          setLoading(false)
        }
      }
    }

    bootstrap()

    return () => {
      cancelled = true
    }
  }, [addToast, isGuest, isHost, user.id])

  if (loading) {
    return <PageLoader />
  }

  return (
    <div className="mx-auto max-w-7xl space-y-8 px-4 py-8 sm:px-6 lg:px-8">
      <section className="rounded-[2rem] border border-gray-200 bg-white p-6 shadow-sm sm:p-8">
        <div className="flex flex-col gap-4 sm:flex-row sm:items-center sm:justify-between">
          <div>
            <span className="inline-flex rounded-full bg-rose-50 px-4 py-1 text-xs font-semibold uppercase tracking-[0.3em] text-primary">
              Dashboard
            </span>
            <h1 className="mt-4 text-3xl font-bold text-gray-900">Olá, {user.nome}</h1>
            <p className="mt-2 text-sm text-gray-500">Acompanhe reservas, imóveis publicados e períodos já ocupados.</p>
          </div>
          <div className="grid gap-3 sm:grid-cols-3">
            <div className="rounded-2xl bg-gray-50 p-4 text-center">
              <LayoutDashboard className="mx-auto mb-2 text-primary" size={18} />
              <p className="text-2xl font-bold text-gray-900">{reservas.length}</p>
              <p className="text-xs uppercase tracking-wide text-gray-500">Reservas</p>
            </div>
            <div className="rounded-2xl bg-gray-50 p-4 text-center">
              <Home className="mx-auto mb-2 text-primary" size={18} />
              <p className="text-2xl font-bold text-gray-900">{imoveis.length}</p>
              <p className="text-xs uppercase tracking-wide text-gray-500">Imóveis</p>
            </div>
            <div className="rounded-2xl bg-gray-50 p-4 text-center">
              <UserRound className="mx-auto mb-2 text-primary" size={18} />
              <p className="text-2xl font-bold text-gray-900">{user.tipo}</p>
              <p className="text-xs uppercase tracking-wide text-gray-500">Perfil</p>
            </div>
          </div>
        </div>
      </section>

      {isGuest && (
        <section className="rounded-[2rem] border border-gray-200 bg-white p-6 shadow-sm sm:p-8">
          <div className="mb-6 flex items-center gap-3">
            <CalendarRange className="text-primary" size={20} />
            <div>
              <h2 className="text-2xl font-bold text-gray-900">Minhas reservas</h2>
              <p className="text-sm text-gray-500">Reservas associadas ao seu perfil de hóspede.</p>
            </div>
          </div>

          {reservas.length === 0 ? (
            <p className="text-sm text-gray-500">Você ainda não possui reservas cadastradas.</p>
          ) : (
            <div className="space-y-4">
              {reservas.map((reserva) => (
                <ReservationCard key={reserva.id} reservation={reserva} />
              ))}
            </div>
          )}
        </section>
      )}

      {isHost && (
        <section className="rounded-[2rem] border border-gray-200 bg-white p-6 shadow-sm sm:p-8">
          <div className="mb-6 flex items-center gap-3">
            <Home className="text-primary" size={20} />
            <div>
              <h2 className="text-2xl font-bold text-gray-900">Meus imóveis</h2>
              <p className="text-sm text-gray-500">Acompanhe os anúncios publicados e os períodos ocupados.</p>
            </div>
          </div>

          {imoveis.length === 0 ? (
            <p className="text-sm text-gray-500">Nenhum imóvel cadastrado para este anfitrião.</p>
          ) : (
            <div className="grid gap-5 lg:grid-cols-2">
              {imoveis.map((imovel) => (
                <article key={imovel.id} className="rounded-2xl border border-gray-100 bg-stone-50 p-5">
                  <div className="flex items-start justify-between gap-3">
                    <div>
                      <h3 className="text-lg font-semibold text-gray-900">{imovel.nome}</h3>
                      <p className="mt-1 text-sm text-gray-500">{imovel.cidade}, {imovel.estado}</p>
                    </div>
                    <span className="rounded-full bg-white px-3 py-1 text-sm font-semibold text-primary shadow-sm">
                      R$ {imovel.preco_por_noite?.toLocaleString('pt-BR')}/noite
                    </span>
                  </div>

                  <p className="mt-4 text-sm leading-6 text-gray-600">{imovel.descricao}</p>

                  <div className="mt-5">
                    <h4 className="mb-3 text-sm font-semibold uppercase tracking-wide text-gray-500">
                      Visualização de períodos ocupados
                    </h4>
                    <OccupancyList periods={ocupacoes[imovel.id] || []} />
                  </div>
                </article>
              ))}
            </div>
          )}
        </section>
      )}
    </div>
  )
}
