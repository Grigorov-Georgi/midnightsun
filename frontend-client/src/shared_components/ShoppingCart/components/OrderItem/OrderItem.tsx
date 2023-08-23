import styles from "./OrderItem.module.scss";
import placeholderImage from "../../../../assets/2-2-space-free-png-image.png";
import { ProductInfo } from "../../../../types/ProductInfo";
import { InputNumber } from "primereact/inputnumber";
import { useState } from "react";
import { FcEmptyTrash } from "react-icons/fc";

export const OrderItem = (props: ProductInfo) => {
  const [quantity, setQuantity] = useState<number>(1);
  const removeProductFromCart = () => {
    console.log("Delete product");
  };
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
      <span
        className={styles.removeIcon}
        onClick={() => removeProductFromCart()}
      >
        <FcEmptyTrash />
      </span>
    </div>
  );
};

export default OrderItem;
