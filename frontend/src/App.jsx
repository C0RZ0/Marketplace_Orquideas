import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Navbar from './components/layout/Navbar';
import Footer from './components/layout/Footer';  
import Home   from './pages/Home';                
import Macetas from './pages/Macetas';   

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
      </Routes>

      {/* Footer también aparece en todas las páginas */}
      <Footer />

    </BrowserRouter>
  );
};

export default App;