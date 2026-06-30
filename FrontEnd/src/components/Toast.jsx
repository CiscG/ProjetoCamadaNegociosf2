import { AlertCircle } from 'lucide-react'

export default function Toast({ toast, onClose }) {
  const bgColor = {
    success: 'bg-green-500',
    error: 'bg-red-500',
    info: 'bg-blue-500',
    warning: 'bg-yellow-500',
  }[toast.type] || 'bg-gray-500'

  return (
    <div className={`${bgColor} animate-slide-in flex items-center gap-2 rounded-2xl p-4 text-white shadow-lg`}>
      <AlertCircle size={20} />
      <span>{toast.message}</span>
      <button onClick={onClose} className="ml-auto text-white transition hover:opacity-80">
        ✕
      </button>
    </div>
  )
}
