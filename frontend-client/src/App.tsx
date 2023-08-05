import { RouterProvider, createBrowserRouter } from "react-router-dom";
import Footer from "./shared_components/Fragments/Footer/Footer";
import { Navbar } from "./shared_components/Fragments/Navbar/Navbar";

const router = createBrowserRouter([
  {
    path: "/",
    element: <div>Home</div>,
  },
  {
    path: "/productCreation",
    element: <div>Product creation page</div>,
  },
  {
    path: "/products",
    element: <div>Products</div>,
  },
  {
    path: "/addProduct",
    element: <div>Add Product</div>,
  },
  {
    path: "/contacts",
    element: <div>Contacts</div>,
  },
  {
    path: "/shoppingCart",
    element: <div>Shopping cart</div>,
  },
  {
    path: "/userPage",
    element: <div>Account</div>,
  },
]);

function App() {
  return (
    <div>
      <Navbar />
      <RouterProvider router={router} />
      <Footer />
    </div>
  );
}

export default App;
