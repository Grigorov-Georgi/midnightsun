import styles from "./Navbar.module.css";
import { AiOutlineHome, AiOutlineShoppingCart } from "react-icons/ai";
import { FiPhone } from "react-icons/fi";
import { Dropdown } from "../../Dropdown/Dropdown";

export const Navbar = () => {
  return (
    <div className={styles.nav}>
      <a href="/" className={styles.link}>
        <AiOutlineHome />
        Home
      </a>
      <div style={{ marginRight: "1rem" }}>
        <Dropdown title="Products" />
      </div>
      <a href="/contacts" className={styles.link}>
        <FiPhone />
        Contacts
      </a>
      <a href="/shoppingCart" className={styles.link}>
        <AiOutlineShoppingCart />
        Shopping cart
      </a>
      {/* <a href="/userPage" className={styles.link}>
        <AiOutlineUser />
        Account
      </a> */}
      <div>Log in</div>
    </div>
  );
};
