import React from 'react'
import { AlertCircle } from 'lucide-react'

export default function Toast({ toast, onClose }) {
  const bgColor = {
    success: 'bg-green-500',
    error: 'bg-red-500',
    info: 'bg-blue-500',
    warning: 'bg-yellow-500',
  }[toast.type] || 'bg-gray-500'

  return (
    <div className={`${bgColor} text-white p-4 rounded shadow-lg flex items-center gap-2`}>
      <AlertCircle size={20} />
      <span>{toast.message}</span>
      <button
        onClick={onClose}
        className="ml-auto text-white hover:opacity-80 transition"
      >
        ✕
      </button>
    </div>
  )
}
