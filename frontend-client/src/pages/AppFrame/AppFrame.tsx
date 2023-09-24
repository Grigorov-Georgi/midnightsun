import { Outlet } from "react-router";
import { Navbar } from "../../shared_components/Fragments/Navbar/Navbar";
import Footer from "../../shared_components/Fragments/Footer/Footer";

const AppFrame = () => {
  return (
    <>
      <Navbar />
      <Outlet />
      <Footer />
    </>
  );
};

export default AppFrame;
