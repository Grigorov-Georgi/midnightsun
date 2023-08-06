import { RouterProvider, createBrowserRouter } from "react-router-dom";
import Footer from "./shared_components/Fragments/Footer/Footer";
import { Navbar } from "./shared_components/Fragments/Navbar/Navbar";
import { ErrorComponent } from "./shared_components/ErrorComponent/ErrorComponent";
import { NewProductForm } from "./shared_components/ProductComponents/NewProductForm/NewProductForm";

const router = createBrowserRouter([
  {
    path: "/",
    element: <div>Home</div>,
    errorElement: <ErrorComponent />,
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
    element: <NewProductForm />,
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
