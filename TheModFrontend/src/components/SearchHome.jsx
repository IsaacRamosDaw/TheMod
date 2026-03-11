// SearchHome.jsx
import '../styles/components/searchHome.css'

export const SearchHome = ({ onClose }) => {
  return (
    <div className="search-overlay" onClick={onClose}>
      <div className="search-modal" onClick={(e) => e.stopPropagation()}>
        <input type="text" autoFocus placeholder="Buscar en la web..." />
        <button onClick={onClose}>Cerrar</button>
      </div>
    </div>
  )
}