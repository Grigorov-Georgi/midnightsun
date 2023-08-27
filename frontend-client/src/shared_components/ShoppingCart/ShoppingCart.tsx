/* eslint-disable react-hooks/exhaustive-deps */
import { Button } from "primereact/button";
import OrderInfo from "./components/OrderInfo/OrderInfo";
import OrderItem from "./components/OrderItem/OrderItem";
import styles from "./ShoppingCart.module.scss";
import { useCartStore } from "../../stores/CartStore";

export const ShoppingCart = () => {
  const itemsInCart = useCartStore((state) => state.orderItems);

  const generateOrderItems = (): JSX.Element[] => {
    const data: JSX.Element[] = [];
    itemsInCart.forEach((product) =>
      data.push(
        <OrderItem
          key={`order-item-${product.info.id}`}
          id={product.info.id}
          name={product.info.name}
          price={product.info.price}
          quantity={product.quantity}
        />
      )
    );
    return data;
  };

  return (
    <div className={styles.shoppingCart}>
      <div className={styles.orderItemsList}>{generateOrderItems()}</div>
      <div className={styles.infoSection}>
        <OrderInfo />
        <Button label={"Order"} className={styles.orderBtn} />
      </div>
    </div>
  );
};

export default ShoppingCart;
