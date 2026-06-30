import { Link } from 'react-router-dom'
import { MapPin, Star, Wifi, Wind, Car, Waves } from 'lucide-react'

const PROPERTY_IMAGES = {
  '75ef00000000000000000001': 'https://images.unsplash.com/photo-1560448204-e02f11c3d0e2?w=600&q=80',
  '75ef00000000000000000002': 'https://images.unsplash.com/photo-1449844908441-895c6697abe2?w=600&q=80',
  '75ef00000000000000000003': 'https://images.unsplash.com/photo-1502672260266-1c1ef2d93688?w=600&q=80',
}

const AMENITY_ICONS = {
  wifi: <Wifi size={13} />,
  'ar-condicionado': <Wind size={13} />,
  'ar condicionado': <Wind size={13} />,
  estacionamento: <Car size={13} />,
  piscina: <Waves size={13} />,
}

export default function ListingCard({ listing }) {
  const { id, nome, cidade, preco_por_noite, comodidades = [], avaliacao, descricao } = listing
  const imageSrc = PROPERTY_IMAGES[id] || `https://picsum.photos/seed/${id}/600/400`

  return (
    <Link to={`/imoveis/${id}`} className="card group block">
      <div className="relative h-56 overflow-hidden">
        <img
          src={imageSrc}
          alt={nome}
          className="h-full w-full object-cover transition-transform duration-500 group-hover:scale-105"
          loading="lazy"
        />
        <div className="absolute left-3 top-3 flex items-center gap-1 rounded-full bg-white/90 px-2.5 py-1 text-xs font-semibold text-gray-800 shadow-sm">
          <Star size={12} className="fill-yellow-400 text-yellow-400" />
          {avaliacao?.toFixed(1)}
        </div>
        <div className="absolute right-3 top-3 rounded-full bg-primary px-3 py-1 text-xs font-bold text-white shadow-sm">
          R$ {preco_por_noite?.toLocaleString('pt-BR')}/noite
        </div>
      </div>

      <div className="space-y-3 p-4">
        <div>
          <h3 className="truncate text-base font-semibold text-gray-900">{nome}</h3>
          <p className="mt-1 flex items-center gap-1 text-sm text-gray-500">
            <MapPin size={13} className="text-primary" />
            {cidade}
          </p>
        </div>

        <p className="line-clamp-2 text-sm leading-6 text-gray-600">{descricao}</p>

        <div className="flex flex-wrap gap-1.5">
          {comodidades.slice(0, 3).map((comodidade) => (
            <span
              key={comodidade}
              className="badge border border-gray-100 bg-gray-50 text-gray-600"
            >
              {AMENITY_ICONS[comodidade.toLowerCase()] || null}
              {comodidade}
            </span>
          ))}
          {comodidades.length > 3 && (
            <span className="badge bg-gray-50 text-gray-500">+{comodidades.length - 3}</span>
          )}
        </div>
      </div>
    </Link>
  )
}
