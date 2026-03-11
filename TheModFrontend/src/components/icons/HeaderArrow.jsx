export const HeaderArrow = ({ isOpen, size="1rem"}) => {
  return (
    <svg
      viewBox="0 0 24 24"
      style={{
        width: size,
        height: size,
        transition: 'transform 0.3s ease',
        transform: isOpen ? 'rotate(180deg)' : 'rotate(0deg)',
        // marginLeft: '5px' // Un pequeño espacio respecto al texto
      }}
      role="presentation"
    >
      <path
        d="M7.41,8.58L12,13.17L16.59,8.58L18,10L12,16L6,10L7.41,8.58Z"
        style={{ fill: 'currentColor' }}
      />
    </svg>
  )
}