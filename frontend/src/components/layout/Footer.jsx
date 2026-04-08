import { Link } from 'react-router-dom';
import './Footer.css';

const Footer = () => {
  return (
    <footer className="footer">

      <div className="footer-contenido">

        {/* Columna 1: nombre de la tienda */}
        <div className="footer-marca">
          <h3>Orquídeas del Combeima</h3>
          <p>Cultivamos belleza natural desde el corazón del Tolima.</p>
        </div>

        {/* Columna 2: enlaces de navegacion */}
        <div className="footer-columna">
          <h4>Páginas</h4>
          <ul>
            <li><Link to="/">Inicio</Link></li>
            <li><Link to="/catalogo">Catálogo</Link></li>
            <li><Link to="/guia">Guía de Cuidado</Link></li>
            <li><Link to="/contacto">Contacto</Link></li>
          </ul>
        </div>

        {/* Columna 3: informacion de contacto */}
        <div className="footer-columna">
          <h4>Contacto</h4>
          <p>Ibagué, Tolima</p>
          <p>WhatsApp disponible</p>
        </div>

      </div>

      <div className="footer-copyright">
        2026 Orquídeas del Combeima — Todos los derechos reservados
      </div>

    </footer>
  );
};

export default Footer;