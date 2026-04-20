import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import {
  AUTH_EVENTS,
  clearToken,
  expireSession,
  formatRemainingTime,
  getRemainingSessionMs,
  getStoredToken,
} from '../../utils/authSession';

const WARNING_THRESHOLD_MS = 5 * 60 * 1000;

const SessionExpiryWarning = () => {
  const navigate = useNavigate();
  const [token, setToken] = useState(() => getStoredToken());
  const [remainingMs, setRemainingMs] = useState(() => (token ? getRemainingSessionMs(token) : 0));
  const [sessionExpired, setSessionExpired] = useState(false);

  useEffect(() => {
    const syncSession = () => {
      const storedToken = getStoredToken();
      setToken(storedToken);
      setSessionExpired(false);
      setRemainingMs(storedToken ? getRemainingSessionMs(storedToken) : 0);
    };

    const markExpired = () => {
      setToken(null);
      setRemainingMs(0);
      setSessionExpired(true);
    };

    window.addEventListener(AUTH_EVENTS.tokenChanged, syncSession);
    window.addEventListener(AUTH_EVENTS.sessionExpired, markExpired);

    return () => {
      window.removeEventListener(AUTH_EVENTS.tokenChanged, syncSession);
      window.removeEventListener(AUTH_EVENTS.sessionExpired, markExpired);
    };
  }, []);

  useEffect(() => {
    if (!token) return undefined;

    const intervalId = window.setInterval(() => {
      setRemainingMs(getRemainingSessionMs(token));
    }, 1000);

    return () => window.clearInterval(intervalId);
  }, [token]);

  useEffect(() => {
    if (!token || remainingMs > 0) return;

    expireSession();
    setToken(null);
    setRemainingMs(0);
    setSessionExpired(true);
  }, [remainingMs, token]);

  if (!token && !sessionExpired) return null;

  const visibleWarning = token && remainingMs > 0 && remainingMs <= WARNING_THRESHOLD_MS;
  const isExpired = sessionExpired || (token && remainingMs <= 0);

  if (!visibleWarning && !isExpired) return null;

  return (
    <section
      style={{
        position: 'sticky',
        top: 0,
        zIndex: 200,
        backgroundColor: isExpired ? '#7f1d1d' : '#92400e',
        color: '#fff',
        padding: '0.85rem 1rem',
        textAlign: 'center',
      }}
    >
      <strong>
        {isExpired
          ? 'Sesion expirada.'
          : `Tu sesion expirara en ${formatRemainingTime(remainingMs)}.`}
      </strong>
      <span style={{ marginLeft: '0.5rem' }}>
        {isExpired
          ? 'Debes iniciar sesion nuevamente.'
          : 'Guarda tu trabajo antes de que cierre la sesion.'}
      </span>
      <button
        onClick={() => {
          clearToken();
          setSessionExpired(false);
          navigate('/login');
        }}
        style={{
          marginLeft: '1rem',
          border: 'none',
          borderRadius: '999px',
          padding: '0.45rem 0.9rem',
          cursor: 'pointer',
          backgroundColor: '#fff',
          color: isExpired ? '#7f1d1d' : '#92400e',
          fontWeight: 'bold',
        }}
      >
        Iniciar sesion
      </button>
    </section>
  );
};

export default SessionExpiryWarning;
