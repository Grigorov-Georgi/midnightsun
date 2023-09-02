import { RouterProvider, createBrowserRouter } from "react-router-dom";
import Footer from "./shared_components/Fragments/Footer/Footer";
import { Navbar } from "./shared_components/Fragments/Navbar/Navbar";
import { ErrorComponent } from "./shared_components/ErrorComponent/ErrorComponent";
import { NewProductForm } from "./shared_components/ProductComponents/NewProductForm/NewProductForm";
import { AllProducts } from "./shared_components/ProductComponents/AllProductsComponent/AllProducts";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import ContactPage from "./shared_components/ContactPage/ContactPage";
import ShoppingCart from "./shared_components/ShoppingCart/ShoppingCart";
import { useEffect } from "react";
import { useAuth0 } from "@auth0/auth0-react";
import { useAuthStore } from "./stores/AuthStore";

const router = createBrowserRouter([
  {
    path: "/",
    element: <div>Home</div>,
    errorElement: <ErrorComponent isPathRelated={true} />,
  },
  {
    path: "/products",
    element: <AllProducts />,
    errorElement: <ErrorComponent isPathRelated={false} />,
  },
  {
    path: "/addProduct",
    element: <NewProductForm />,
    errorElement: <ErrorComponent isPathRelated={false} />,
  },
  {
    path: "/contacts",
    element: <ContactPage />,
  },
  {
    path: "/shoppingCart",
    element: <ShoppingCart />,
    errorElement: <ErrorComponent isPathRelated={false} />,
  },
  {
    path: "/userPage",
    element: <div>Account</div>,
  },
]);

const queryClient = new QueryClient();

function App() {
  const { getAccessTokenSilently } = useAuth0();
  const refreshToken = useAuthStore((state) => state.refreshToken);

  useEffect(() => {
    const fetchAccessToken = async () => {
      const token = await getAccessTokenSilently();
      return token;
    };
    fetchAccessToken().then((newToken: string) => {
      if (newToken.length > 0) refreshToken(newToken);
    });
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [getAccessTokenSilently]);

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
