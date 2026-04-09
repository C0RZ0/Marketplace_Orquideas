import './ProductCard.css';
import Button from './Button'; // Reutilizamos el Button que ya tienes

// Props que recibe esta tarjeta:
//   nombre  → "Orquídea Phalaenopsis"
//   precio  → 45000
//   imagen  → URL de la foto (si no hay, muestra un placeholder)
//   badge   → "Novedad" o "Oferta" (si no se pasa, no aparece nada)
//   stock   → 8 (número de unidades disponibles)

const ProductCard = ({ nombre, precio, imagen, badge, stock }) => {
  return (
    <div className="product-card">

      {/* Parte de arriba: imagen + badge */}
      <div className="product-card-imagen-wrapper">

        <img
          src={imagen || 'https://placehold.co/240x200?text=Orquidea'}
          alt={nombre}
          className="product-card-imagen"
        />

        {/* El && significa: "solo muestra esto si badge tiene valor" */}
        {badge && (
          <span className="product-card-badge">{badge}</span>
        )}

      </div>

      {/* Parte de abajo: texto y botón */}
      <div className="product-card-info">

        <h3 className="product-card-nombre">{nombre}</h3>

        {/* toLocaleString convierte 45000 en "45.000" (formato colombiano) */}
        <p className="product-card-precio">
          ${precio?.toLocaleString('es-CO')}
        </p>

        {/* Cambia de clase CSS según si hay stock o no */}
        <p className={`product-card-stock ${stock > 0 ? 'disponible' : 'agotado'}`}>
          {stock > 0 ? `${stock} disponibles` : 'Agotado'}
        </p>

        <Button
          text="Agregar al carrito"
          onClick={() => alert(`Agregaste ${nombre} al carrito`)}
        />

      </div>
    </div>
  );
};

export default ProductCard;