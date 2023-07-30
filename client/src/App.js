import React from 'react';
import './App.css';
import Navbar from './components/Fragments/Navbar/Navbar';
import Footer from './components/Fragments/Footer/Footer';
import BasicCarousel from './components/Carousel/Carousel';
import ProductForm from './components/ProductForm';
import ProductGrid from './components/Product/ProductsGrid'
import ProductDetails from './components/Product/ProductDetails';

function App() {
  return (
    <div>
      <Navbar />
      {/* <BasicCarousel /> */}
      {/* <ProductForm /> */}
      {/* <ProductGrid /> */}
      <ProductDetails />
      <Footer />
    </div>
  );
}

export default App;
