import { useEffect, useRef } from 'react';
import Loading from '../components/ui/Loading';
import ConnectionError from '../components/ui/ConnectionError';
import useConnectionCheck from '../hooks/useConnectionCheck';

// Coordenadas del negocio
const LAT = 4.453806;
const LNG = -75.243278;

const Contacto = () => {
  const { loading, error, retry } = useConnectionCheck();
  const mapaRef = useRef(null);

  useEffect(() => {
    if (loading || error) return;

    const iniciarMapa = () => {
      if (!mapaRef.current || !window.google) return;

      const mapa = new window.google.maps.Map(mapaRef.current, {
        center: { lat: LAT, lng: LNG },
        zoom: 16,
      });

      new window.google.maps.Marker({
        position: { lat: LAT, lng: LNG },
        map: mapa,
        title: 'Orquídeas del Combeima',
      });
    };

    // Si ya está cargado el script
    if (window.google) {
      iniciarMapa();
      return;
    }

    // Cargar el script de Google Maps dinámicamente
    const script = document.createElement('script');
    script.src = `https://maps.googleapis.com/maps/api/js?key=${import.meta.env.VITE_GOOGLE_MAPS_KEY}`;
    script.async = true;
    script.onload = iniciarMapa;
    document.head.appendChild(script);

  }, [loading, error]);

  if (loading) return <Loading mensaje="Cargando pagina..." />;
  if (error) return <ConnectionError mensaje={error} onRetry={retry} />;

  return (
      <div>
        <div style={{ backgroundColor: '#1B4332', color: '#FAF7F5', padding: '3rem 2rem', textAlign: 'center' }}>
          <h1>Contacto</h1>
          <p>Estamos aquí para ayudarte</p>
        </div>

        <div style={{ maxWidth: '800px', margin: '0 auto', padding: '3rem 2rem', display: 'flex', flexDirection: 'column', gap: '1.5rem' }}>

          <div style={{ backgroundColor: '#FFFFFF', borderRadius: '12px', padding: '1.5rem', boxShadow: '0 2px 8px rgba(0,0,0,0.08)' }}>
            <h2 style={{ color: '#2D6A4F', marginBottom: '0.8rem' }}>WhatsApp</h2>
            <p style={{ color: '#1B4332', marginBottom: '1rem' }}>La forma más rápida de comunicarte con nosotros.</p>
            <a
            href="https://wa.me/573014791094"
            target="_blank"
            rel="noreferrer"
            style={{ backgroundColor: '#2D6A4F', color: '#FAF7F5', padding: '0.6rem 1.4rem', borderRadius: '20px', textDecoration: 'none', display: 'inline-block' }}
            >
            Escríbenos por WhatsApp
          </a>
        </div>

        <div style={{ backgroundColor: '#FFFFFF', borderRadius: '12px', padding: '1.5rem', boxShadow: '0 2px 8px rgba(0,0,0,0.08)' }}>
          <h2 style={{ color: '#2D6A4F', marginBottom: '0.8rem' }}>Correo electrónico</h2>
          <p style={{ color: '#1B4332' }}>admin.orquicombeima@gmail.com</p>
        </div>

        <div style={{ backgroundColor: '#FFFFFF', borderRadius: '12px', padding: '1.5rem', boxShadow: '0 2px 8px rgba(0,0,0,0.08)' }}>
          <h2 style={{ color: '#2D6A4F', marginBottom: '0.8rem' }}>Ubicación</h2>
          <p style={{ color: '#1B4332', marginBottom: '1rem' }}>Ibagué, Tolima, Colombia</p>
          <div
              ref={mapaRef}
              style={{ width: '100%', height: '300px', borderRadius: '8px' }}
          />
        </div>

      </div>
</div>
);
};

export default Contacto;