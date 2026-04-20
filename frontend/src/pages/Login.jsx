import { useEffect } from 'react';
import Loading from '../components/ui/Loading';
import api from '../services/api';

const Login = () => {
  useEffect(() => {
    const backendBaseUrl = api.defaults.baseURL.replace('/api', '');
    window.location.replace(`${backendBaseUrl}/oauth2/authorization/google`);
  }, []);

  return <Loading mensaje="Redirigiendo al inicio de sesion..." />;
};

export default Login;
