/* eslint-disable @typescript-eslint/no-unused-vars */
import styles from "./Navbar.module.scss";
import { AiOutlineHome, AiOutlineShoppingCart } from "react-icons/ai";
import { FiPhone } from "react-icons/fi";
import { Dropdown } from "../../Dropdown/Dropdown";
import { useAuth0 } from "@auth0/auth0-react";
import { Link } from "react-router-dom";

export const Navbar = () => {
  const { isAuthenticated, logout, loginWithRedirect } = useAuth0();

  return (
    <div className={styles.nav}>
      <Link to="/" className={styles.link}>
        <AiOutlineHome />
        Home
      </Link>
      <div style={{ marginRight: "1rem" }}>
        <Dropdown title="Products" />
      </div>
      <Link to="/contacts" className={styles.link}>
        <FiPhone />
        Contacts
      </Link>
      <Link to="/shoppingCart" className={styles.link}>
        <AiOutlineShoppingCart />
        Shopping cart
      </Link>
      {/* <Link to="/userPage" className={styles.link}>
        <AiOutlineUser />
        Account
  </Link> */}
      <div
        className={`${styles.link} ${styles.divLink}`}
        onClick={() =>
          !isAuthenticated
            ? loginWithRedirect()
            : logout({
                logoutParams: {
                  returnTo: window.location.origin,
                },
              })
        }
      >
        {`${isAuthenticated ? "Log out" : "Log in"}`}
      </div>
    </div>
  );
};
