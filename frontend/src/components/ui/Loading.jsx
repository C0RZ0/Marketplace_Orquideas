// Traemos los estilos que acabamos de crear
import './Loading.css';

// Este componente recibe una prop llamada "mensaje"
// Si no le pasas nada, por defecto dice "Cargando..."
// Ejemplo de uso: <Loading />
// Ejemplo con texto propio: <Loading mensaje="Buscando orquídeas..." />

const Loading = ({ mensaje = "Cargando..." }) => {
  return (
    <div className="loading-container">

      {/* El círculo que gira — los estilos están en Loading.css */}
      <div className="loading-spinner" />

      {/* El texto que viene de la prop */}
      <p className="loading-texto">{mensaje}</p>

    </div>
  );
};

export default Loading;