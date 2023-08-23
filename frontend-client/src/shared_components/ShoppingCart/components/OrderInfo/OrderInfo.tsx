import styles from "./OrderInfo.module.scss";

export const OrderInfo = () => {
  return (
    <div className={styles.infoSection}>
      <span>Total price: </span>
      <h2>100.00$</h2>
      <span>Shipping method: Courier</span>
      <span>Payment: Upon delivery</span>
    </div>
  );
};

export default OrderInfo;
