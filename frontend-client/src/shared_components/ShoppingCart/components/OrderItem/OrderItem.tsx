import styles from "./OrderItem.module.scss";
import placeholderImage from "../../../../assets/2-2-space-free-png-image.png";
import { ProductInfo } from "../../../../types/ProductInfo";
import { InputNumber } from "primereact/inputnumber";
import { useState } from "react";

export const OrderItem = (props: ProductInfo) => {
  const [quantity, setQuantity] = useState<number>(1);

  return (
    <div className={styles.orderItem}>
      <img
        src={placeholderImage}
        alt="Product image"
        className={styles.image}
      />
      <span className={styles.name}>{props.name}</span>
      <InputNumber
        value={quantity}
        onValueChange={(e) => setQuantity(e.value as number)}
        showButtons={true}
        buttonLayout="vertical"
        className={styles.quantity}
        incrementButtonClassName={styles.btn}
        decrementButtonClassName={styles.btn}
        inputClassName={styles.input}
      />
      <span>{props.price}$</span>
    </div>
  );
};

export default OrderItem;
