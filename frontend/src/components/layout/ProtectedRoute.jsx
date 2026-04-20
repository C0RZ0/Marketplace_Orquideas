// ProtectedRoute.jsx
// Protege rutas que requieren estar logueado
// Si el usuario no tiene sesion, lo manda al login
// Uso: <ProtectedRoute> <PaginaProtegida /> </ProtectedRoute>

import { Navigate, useLocation } from 'react-router-dom';
import useAuth from '../../hooks/useAuth';
import { savePostLoginRedirect } from '../../utils/authFlowStorage';

const ProtectedRoute = ({ children }) => {
  const { isLoggedIn } = useAuth();
  const location = useLocation();

  if (!isLoggedIn) {
    // Modificado (Matt): guardamos destino para volver tras autenticarse
    savePostLoginRedirect(`${location.pathname}${location.search || ''}`);
    // Navigate reemplaza la pagina actual por /login
    return <Navigate to="/login" state={{ from: location.pathname }} replace />;
  }

  return children;
};

export default ProtectedRoute;