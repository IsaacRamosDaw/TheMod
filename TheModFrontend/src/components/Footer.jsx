import '../styles/components/footer.css'
import { FOOTER_SOCIAL_DATA, FOOTER_MENU_OPTIONS } from './values/menusOptions'
export function Footer() {

  return (
    <footer className="main-footer">
      <div className="footer-container">

        {/* Secciones de enlaces dinámicas */}

        <div className="footer-links-grid">
          {Object.entries(FOOTER_MENU_OPTIONS).map(([title, links]) => (
            <div key={title} className="footer-column">
              <h4 className="footer-title">{title}</h4>
              <ul className="footer-list">
                {links.map(link => (
                  <li key={link}><a href="#">{link}</a></li>
                ))}
              </ul>
            </div>
          ))}

          {/* Sección de Soporte y Stats */}
          <div className="footer-column support-section">
            <h4 className="footer-title">SUPPORT NEXUS MODS</h4>
            <a href="#" className="premium-button">Go Premium</a>

            <div className="network-stats">
              <h4 className="footer-title">NETWORK STATS</h4>
              <div className="stats-grid">
                <div>
                  <span className="stat-label">Members</span>
                  <span className="stat-value">71,719,057</span>
                </div>
                <div>
                  <span className="stat-label">Mods</span>
                  <span className="stat-value">821,307</span>
                </div>
                <div>
                  <span className="stat-label">Kudos</span>
                  <span className="stat-value">3,874,758</span>
                </div>
              </div>
              <div className="server-info">
                <p>Server info</p>
                <span>Served in 0.267s</span>
                <span>3.0.1882 | UK 3</span>
              </div>
            </div>
          </div>
        </div>

        {/* Barra inferior con logo y redes */}
        <div className="footer-bottom">
          <div className="footer-logo">
            <img src="/header-logo.png" alt="Nexus Mods" />
            <span className="logo-text">NEXUSMODS</span>
          </div>
          <div className="social-icons">
            {FOOTER_SOCIAL_DATA.map(social => (
              <a className='social-link' key={social.id} href="#">
                <svg
                  viewBox="0 0 24 24"
                  width="24"
                  height="24"
                  fill="currentColor"
                >
                  {/* Aquí inyectas el string que guardaste en el array */}
                  <path d={social.icon} />
                </svg>
              </a>
            ))}
          </div>
        </div>
      </div>

    </footer>
  )
}