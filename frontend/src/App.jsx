import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Navbar from './components/layout/Navbar';
import Footer from './components/layout/Footer';  // ← nuevo
import Home   from './pages/Home';                // ← nuevo

const App = () => {
  return (
    <BrowserRouter>

      {/* Navbar aparece en todas las páginas */}
      <Navbar />

      <Routes>
        {/* La página de inicio */}
        <Route path="/" element={<Home />} />
      </Routes>

      {/* Footer también aparece en todas las páginas */}
      <Footer />

    </BrowserRouter>
  );
};

export default App;