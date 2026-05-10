// WhatsAppBoton.jsx
import { useState, useEffect } from 'react';
import axios from 'axios';
import './WhatsAppBoton.css';

const WhatsAppBoton = () => {
    const [urlWhatsapp, setUrlWhatsapp] = useState('https://wa.me/573014791094');

    useEffect(() => {
        axios.get('/api/contacto')
            .then(res => setUrlWhatsapp(res.data.urlWhatsapp))
            .catch(() => {});
    }, []);

    return (
        <a
            href={urlWhatsapp}
            target="_blank"
            rel="noreferrer"
            className="whatsapp-boton"
        >
        <img
            src="/whatsapp.png"
            alt="Contacto por WhatsApp"
            className="whatsapp-icono"
        />
        </a>
);
};

export default WhatsAppBoton;