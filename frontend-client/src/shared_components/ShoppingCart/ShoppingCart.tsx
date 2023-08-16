import { Button } from "primereact/button";
import OrderInfo from "./components/OrderInfo/OrderInfo";
import OrderItem from "./components/OrderItem/OrderItem";
import styles from "./ShoppingCart.module.scss";

export const ShoppingCart = () => {
  const generateDummyData = (): JSX.Element[] => {
    const data: JSX.Element[] = [];
    for (let i = 0; i < 10; i++) {
      data.push(<OrderItem />);
    }
    return data;
  };

  return (
    <div className={styles.shoppingCart}>
      <div className={styles.orderItemsList}>{generateDummyData()}</div>
      <div className={styles.infoSection}>
        <OrderInfo />
        <Button label={"Order"} className={styles.orderBtn} />
      </div>
    </div>
  );
};

export default ShoppingCart;
