import { useEffect, useState } from 'react';
import { useNavigate, useSearchParams } from 'react-router-dom';
import Loading from '../components/ui/Loading';
import ConnectionError from '../components/ui/ConnectionError';
import { saveToken } from '../utils/authSession';

const AuthCallback = () => {
  const [searchParams] = useSearchParams();
  const navigate = useNavigate();
  const [error, setError] = useState('');

  useEffect(() => {
    const token = searchParams.get('token');

    if (!token) {
      setError('No se recibio un token valido desde el inicio de sesion.');
      return;
    }

    saveToken(token);
    navigate('/', { replace: true });
  }, [navigate, searchParams]);

  if (error) {
    return <ConnectionError mensaje={error} onRetry={() => navigate('/login')} />;
  }

  return <Loading mensaje="Finalizando autenticacion..." />;
};

export default AuthCallback;
