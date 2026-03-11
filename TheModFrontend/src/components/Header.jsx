import { useState } from 'react'
import { HeaderArrow } from './icons/HeaderArrow'
import { DropDownMenu } from './DropDownMenu'
import { HEADER_MENU_DATA } from './values/menusOptions'

import '../styles/components/header.css'
import { SearchHome } from './SearchHome'


export function Header() {
  // Opciones del menú del header
  const [isLogged, setIsLogged] = useState(false)
  const menuOptions = Object.keys(HEADER_MENU_DATA)
  const [searchOpen, setSearchOpen] = useState(false)
  const [isOpen, setIsOpen] = useState(null)


  const toggleMenu = (menuName) => {
    setIsOpen(isOpen === menuName ? null : menuName)
  }

  return (
    <header className='header'>
      <div className='page-logo'>
        <img src="/header-logo.png" alt="" className='logo-header' />
      </div>
      <nav className='header-nav'>
        <ul>
          {menuOptions.map((option) => (
            <li key={option}>
              <button
                className='header-button-menu'
                onClick={() => toggleMenu(option)}
                aria-expanded={isOpen === option}
              >
                {option}
                <HeaderArrow isOpen={isOpen === option} />
              </button>

              {isOpen === option && (
                <DropDownMenu options={HEADER_MENU_DATA[option]} />
              )}
            </li>
          ))}
        </ul>
      </nav>
      <div className='header-search-input'>
        <input type="text" placeholder='Search' onClick={() => setSearchOpen(true)} />
      </div>
      <div className='header-buttons'>
        {isLogged ? (
          <button className='header-button-login'>Perfil</button>
        ) : (
          <a href="/login" className='header-button-login'>Login</a>
        )}
      </div>
      {isOpen && (
        <DropDownMenu options={HEADER_MENU_DATA[isOpen]} />
      )}
      {searchOpen && (
        <SearchHome onClose={() => setSearchOpen(false)} />
      )}
    </header>
  )
}