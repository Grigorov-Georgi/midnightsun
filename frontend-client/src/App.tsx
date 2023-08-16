import { RouterProvider, createBrowserRouter } from "react-router-dom";
import Footer from "./shared_components/Fragments/Footer/Footer";
import { Navbar } from "./shared_components/Fragments/Navbar/Navbar";
import { ErrorComponent } from "./shared_components/ErrorComponent/ErrorComponent";
import { NewProductForm } from "./shared_components/ProductComponents/NewProductForm/NewProductForm";
import { AllProducts } from "./shared_components/ProductComponents/AllProductsComponent/AllProducts";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import ContactPage from "./shared_components/ContactPage/ContactPage";

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
    element: <AllProducts />,
  },
  {
    path: "/addProduct",
    element: <NewProductForm />,
  },
  {
    path: "/contacts",
    element: <ContactPage />,
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

const queryClient = new QueryClient();

function App() {
  return (
    <div>
      <QueryClientProvider client={queryClient}>
        <Navbar />
        <RouterProvider router={router} />
        <Footer />
      </QueryClientProvider>
    </div>
  );
}

export default App;
