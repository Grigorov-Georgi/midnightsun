import { useCartStore } from "../../../../stores/CartStore";
import styles from "./OrderInfo.module.scss";

export const OrderInfo = () => {
  const totalPrice = useCartStore((state) => state.totalPrice);

  return (
    <div className={styles.infoSection}>
      <span>Total price: </span>
      <h2>{totalPrice}$</h2>
      <span>Shipping method: Courier</span>
      <span>Payment: Upon delivery</span>
    </div>
  );
};

export default OrderInfo;
