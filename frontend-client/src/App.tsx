import "./App.css";
import BasicCarousel from "./shared_components/Carousel/Carousel";
import Footer from "./shared_components/Fragments/Footer/Footer";
import Navbar from "./shared_components/Fragments/Navbar/Navbar";

function App() {
  return (
    <div>
      <Navbar />

      {/* //Home page */}
      <BasicCarousel />

      {/* //Add Product - Edit Optional */}
      {/* <ProductForm /> */}

      {/* //List All Products - Catalog */}
      {/* <ProductGrid /> */}

      {/* //Product Details */}
      {/* <ProductDetails /> */}
      <Footer />
    </div>
  );
}

export default App;
