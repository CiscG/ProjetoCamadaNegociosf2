import { useEffect, useState } from 'react'
import { SlidersHorizontal } from 'lucide-react'
import { api } from '../api/client'
import ListingCard from '../components/ListingCard'
import SearchBar from '../components/SearchBar'
import { PageLoader } from '../components/LoadingSpinner'
import { useToast } from '../context/ToastContext'

const DEFAULT_FILTERS = {
  cidade: '',
  preco_max: '',
  avaliacao: '',
  checkin: '',
  checkout: '',
}

function rangesOverlap(startA, endA, startB, endB) {
  return new Date(startA) < new Date(endB) && new Date(endA) > new Date(startB)
}

export default function HomePage() {
  const [filters, setFilters] = useState(DEFAULT_FILTERS)
  const [listings, setListings] = useState([])
  const [loading, setLoading] = useState(true)
  const { addToast } = useToast()

  const loadListings = async (nextFilters = DEFAULT_FILTERS) => {
    setLoading(true)

    try {
      if (nextFilters.checkin && nextFilters.checkout && nextFilters.checkin >= nextFilters.checkout) {
        throw new Error('A data de check-out deve ser posterior ao check-in.')
      }

      let locais = await api.getLocais(nextFilters)

      if (nextFilters.checkin && nextFilters.checkout) {
        const ocupacoes = await Promise.all(
          locais.map(async (local) => ({
            localId: local.id,
            periodos: await api.getOcupacao(local.id).catch(() => []),
          }))
        )

        const mapaOcupacao = Object.fromEntries(
          ocupacoes.map((item) => [item.localId, item.periodos])
        )

        locais = locais.filter((local) =>
          !(mapaOcupacao[local.id] || []).some((periodo) =>
            rangesOverlap(nextFilters.checkin, nextFilters.checkout, periodo.desde, periodo.ate)
          )
        )
      }

      setListings(locais)
    } catch (error) {
      addToast(error.message || 'Erro ao carregar imóveis.', 'error')
    } finally {
      setLoading(false)
    }
  }

  useEffect(() => {
    let cancelled = false

    async function bootstrap() {
      setLoading(true)

      try {
        const locais = await api.getLocais(DEFAULT_FILTERS)
        if (!cancelled) {
          setListings(locais)
        }
      } catch (error) {
        if (!cancelled) {
          addToast(error.message || 'Erro ao carregar imóveis.', 'error')
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
  }, [addToast])

  const handleFilterChange = (field, value) => {
    setFilters((current) => ({ ...current, [field]: value }))
  }

  const handleSearch = async (event) => {
    event.preventDefault()
    await loadListings(filters)
  }

  const handleClear = async () => {
    setFilters(DEFAULT_FILTERS)
    await loadListings(DEFAULT_FILTERS)
  }

  if (loading && listings.length === 0) {
    return <PageLoader />
  }

  return (
    <div className="mx-auto max-w-7xl px-4 py-8 sm:px-6 lg:px-8">
      <section className="rounded-[2rem] bg-gradient-to-r from-rose-500 via-primary to-rose-600 px-6 py-10 text-white shadow-lg sm:px-10">
        <div className="max-w-3xl">
          <span className="inline-flex rounded-full bg-white/15 px-4 py-1 text-xs font-semibold uppercase tracking-[0.3em]">
            Busca inteligente
          </span>
          <h1 className="mt-5 text-3xl font-bold sm:text-5xl">Encontre o imóvel ideal para sua próxima hospedagem.</h1>
          <p className="mt-4 text-sm leading-7 text-rose-50 sm:text-base">
            Pesquise por cidade, preço máximo, avaliação e disponibilidade por datas para reservar com mais segurança.
          </p>
        </div>
      </section>

      <section className="-mt-8 relative z-10">
        <SearchBar
          filters={filters}
          onChange={handleFilterChange}
          onSubmit={handleSearch}
          onClear={handleClear}
          loading={loading}
        />
      </section>

      <section className="mt-10">
        <div className="mb-6 flex flex-col gap-3 sm:flex-row sm:items-center sm:justify-between">
          <div>
            <h2 className="text-2xl font-bold text-gray-900">Imóveis disponíveis</h2>
            <p className="mt-1 text-sm text-gray-500">{listings.length} resultado{listings.length === 1 ? '' : 's'} encontrado{listings.length === 1 ? '' : 's'}.</p>
          </div>
          <div className="inline-flex items-center gap-2 rounded-full bg-white px-4 py-2 text-sm text-gray-500 shadow-sm">
            <SlidersHorizontal size={16} className="text-primary" />
            Busca por cidade, valor, avaliação e datas
          </div>
        </div>

        {loading ? (
          <PageLoader />
        ) : listings.length === 0 ? (
          <div className="rounded-[2rem] border border-dashed border-gray-300 bg-white px-6 py-16 text-center shadow-sm">
            <h3 className="text-xl font-semibold text-gray-900">Nenhum imóvel encontrado</h3>
            <p className="mt-2 text-sm text-gray-500">Ajuste os filtros para encontrar outras hospedagens disponíveis.</p>
          </div>
        ) : (
          <div className="grid gap-6 md:grid-cols-2 xl:grid-cols-3">
            {listings.map((listing) => (
              <ListingCard key={listing.id} listing={listing} />
            ))}
          </div>
        )}
      </section>
    </div>
  )
}
