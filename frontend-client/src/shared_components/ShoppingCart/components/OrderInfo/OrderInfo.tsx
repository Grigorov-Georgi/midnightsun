import { useEffect, useRef } from "react";
import { useCartStore } from "../../../../stores/CartStore";
import styles from "./OrderInfo.module.scss";

export const OrderInfo = () => {
  const totalPrice = useCartStore((state) => state.totalPrice);
  const calculateTotalPrice = useCartStore(
    (state) => state.calculateTotalPrice
  );
  const hasMounted = useRef(false);

  useEffect(() => {
    if (!hasMounted.current) {
      hasMounted.current = true;
      calculateTotalPrice();
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

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
