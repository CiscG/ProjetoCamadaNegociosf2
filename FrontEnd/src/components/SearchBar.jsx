const RATINGS = [
  { value: '', label: 'Qualquer avaliação' },
  { value: '4.5', label: '4.5+ estrelas' },
  { value: '4.8', label: '4.8+ estrelas' },
]

export default function SearchBar({ filters, onChange, onSubmit, onClear, loading }) {
  return (
    <form
      onSubmit={onSubmit}
      className="rounded-[2rem] border border-gray-200 bg-white p-4 shadow-sm sm:p-5"
    >
      <div className="grid gap-3 md:grid-cols-2 xl:grid-cols-5">
        <label className="flex flex-col gap-1 rounded-2xl border border-transparent px-1 py-1 md:col-span-2 xl:col-span-1">
          <span className="text-xs font-semibold uppercase tracking-wide text-gray-500">Cidade</span>
          <input
            type="text"
            value={filters.cidade}
            onChange={(event) => onChange('cidade', event.target.value)}
            placeholder="Ex.: Rio de Janeiro"
            className="input-field"
          />
        </label>

        <label className="flex flex-col gap-1 rounded-2xl border border-transparent px-1 py-1">
          <span className="text-xs font-semibold uppercase tracking-wide text-gray-500">Preço máximo</span>
          <input
            type="number"
            min="0"
            step="0.01"
            value={filters.preco_max}
            onChange={(event) => onChange('preco_max', event.target.value)}
            placeholder="Até R$ 500"
            className="input-field"
          />
        </label>

        <label className="flex flex-col gap-1 rounded-2xl border border-transparent px-1 py-1">
          <span className="text-xs font-semibold uppercase tracking-wide text-gray-500">Avaliação</span>
          <select
            value={filters.avaliacao}
            onChange={(event) => onChange('avaliacao', event.target.value)}
            className="input-field"
          >
            {RATINGS.map((rating) => (
              <option key={rating.value || 'all'} value={rating.value}>
                {rating.label}
              </option>
            ))}
          </select>
        </label>

        <label className="flex flex-col gap-1 rounded-2xl border border-transparent px-1 py-1">
          <span className="text-xs font-semibold uppercase tracking-wide text-gray-500">Check-in</span>
          <input
            type="date"
            value={filters.checkin}
            onChange={(event) => onChange('checkin', event.target.value)}
            className="input-field"
          />
        </label>

        <label className="flex flex-col gap-1 rounded-2xl border border-transparent px-1 py-1">
          <span className="text-xs font-semibold uppercase tracking-wide text-gray-500">Check-out</span>
          <input
            type="date"
            min={filters.checkin || undefined}
            value={filters.checkout}
            onChange={(event) => onChange('checkout', event.target.value)}
            className="input-field"
          />
        </label>
      </div>

      <div className="mt-4 flex flex-col gap-3 sm:flex-row sm:items-center sm:justify-end">
        <button type="button" onClick={onClear} className="btn-ghost justify-center">
          Limpar filtros
        </button>
        <button type="submit" disabled={loading} className="btn-primary justify-center">
          {loading ? 'Buscando...' : 'Buscar imóveis'}
        </button>
      </div>
    </form>
  )
}
