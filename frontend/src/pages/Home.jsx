import ProductCard from '../components/ui/ProductCard';

// Datos de prueba — en semana 2/3 vendrán del backend real
const orquideasDestacadas = [
  { id: 1, nombre: 'Orquídea Phalaenopsis', precio: 45000, stock: 8,  badge: 'Novedad' },
  { id: 2, nombre: 'Orquídea Cattleya',     precio: 62000, stock: 3,  badge: 'Oferta'  },
  { id: 3, nombre: 'Orquídea Dendrobium',   precio: 38000, stock: 0,  badge: null      },
];

const Home = () => {
  return (
    <main>

      {/* Seccion principal de bienvenida */}
      <section style={{
        background: 'linear-gradient(135deg, #1B4332 0%, #2D6A4F 100%)',
        color: '#FAF7F5',
        padding: '5rem 2rem',
        textAlign: 'center'
      }}>
        <h1 style={{ fontSize: '2.5rem', marginBottom: '1rem' }}>
          Bienvenido a Orquídeas del Combeima
        </h1>
        <p style={{ fontSize: '1.1rem', marginBottom: '2rem', opacity: 0.9 }}>
          Las orquídeas más hermosas del Tolima, directo a tu hogar
        </p>

        {/* Buscador — la funcionalidad real se conecta en semana 3 */}
        <div style={{ display: 'flex', justifyContent: 'center', gap: '0.5rem' }}>
          <input
            type="text"
            placeholder="Buscar orquídeas..."
            style={{
              padding: '0.7rem 1.2rem',
              borderRadius: '20px',
              border: 'none',
              width: '300px',
              fontSize: '0.95rem'
            }}
          />
          <button style={{
            backgroundColor: '#E91E8C',
            color: '#fff',
            border: 'none',
            borderRadius: '20px',
            padding: '0.7rem 1.5rem',
            cursor: 'pointer',
            fontSize: '0.95rem'
          }}>
            Buscar
          </button>
        </div>
      </section>

      {/* Badges de confianza */}
      <section style={{
        display: 'flex',
        justifyContent: 'center',
        gap: '3rem',
        flexWrap: 'wrap',
        padding: '2rem',
        backgroundColor: '#fff'
      }}>
        {[
          { icono: 'Garantia de Calidad', descripcion: 'Garantia de Calidad' },
          { icono: 'Pago Seguro',         descripcion: 'Pago Seguro'         },
        ].map(({ descripcion }) => (
          <div key={descripcion} style={{ textAlign: 'center' }}>
            <p style={{ color: '#1B4332', fontWeight: 'bold' }}>{descripcion}</p>
          </div>
        ))}
      </section>

      {/* Orquideas destacadas */}
      <section style={{ padding: '3rem 2rem', maxWidth: '1200px', margin: '0 auto' }}>
        <h2 style={{ color: '#1B4332', marginBottom: '2rem', textAlign: 'center' }}>
          Orquídeas Destacadas
        </h2>

        <div style={{ display: 'flex', gap: '1.5rem', flexWrap: 'wrap', justifyContent: 'center' }}>
          {orquideasDestacadas.map(orquidea => (
            <ProductCard
              key={orquidea.id}
              nombre={orquidea.nombre}
              precio={orquidea.precio}
              stock={orquidea.stock}
              badge={orquidea.badge}
            />
          ))}
        </div>
      </section>

    </main>
  );
};

export default Home;