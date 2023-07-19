import React from 'react';
import './App.css';
import Navbar from './components/Fragments/Navbar/Navbar';
import Footer from './components/Fragments/Footer/Footer';
import BasicCarousel from './components/Carousel/Carousel';

function App() {
  return (
    <div>
      <Navbar />
      <BasicCarousel />
      <Footer />
    </div>
  );
}

export default App;
