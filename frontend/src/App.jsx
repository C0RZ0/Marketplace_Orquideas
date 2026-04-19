import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Navbar from './components/layout/Navbar';
import Footer from './components/layout/Footer';  
import Home   from './pages/Home';                
import Macetas from './pages/Macetas';   
import Guia from './pages/Guia';
import Contacto from './pages/Contacto';
import Catalogo from './pages/Catalogo';
import DetalleOrquidea from './pages/DetalleOrquidea';
import NotFound from './pages/NotFound';
import WhatsAppBoton from './components/ui/WhatsAppBoton';
import Login from './pages/Login';
import ProtectedRoute from './components/layout/ProtectedRoute';



const App = () => {
  return (
    <BrowserRouter>

      {/* Navbar aparece en todas las páginas */}
      <Navbar />

      <Routes>
        {/* La página de inicio */}
        <Route path="/" element={<Home />} />
        {/* Página de catálogo de macetas */}
        <Route path="/macetas" element={<Macetas />} />
        {/* Página de guía de cuidado */}
        <Route path="/guia" element={<Guia />} />
        {/* Página de contacto */}
        <Route path="/contacto" element={<Contacto />} />
        {/* Página de catálogo de orquídeas */}
        <Route path="/catalogo" element={<Catalogo />} />
        {/* Página de detalle de orquídea */}
        <Route path="/orquideas/:id" element={<DetalleOrquidea />} />
        {/* Cualquier ruta inexistente */}
        <Route path="*" element={<NotFound />} />
        {/* Página de login */}
        <Route path="/login" element={<Login />} />
        {/* Ejemplo de ruta protegida — el carrito solo lo ven usuarios logueados */}
        <Route path="/carrito" element={
          <ProtectedRoute>
            <h1>Carrito — proximamente</h1>
          </ProtectedRoute>
        } />
        {/* Otras rutas se agregan aquí en el futuro */}

      </Routes>

      {/* Footer también aparece en todas las páginas */}
      <Footer />
      {/* Botón flotante de WhatsApp */}
      <WhatsAppBoton />

    </BrowserRouter>
  );
};

export default App;