import styles from "./OrderItem.module.scss";
import placeholderImage from "../../../../assets/2-2-space-free-png-image.png";
import { ProductInfo } from "../../../../types/ProductInfo";
import { InputNumber } from "primereact/inputnumber";
import { FcEmptyTrash } from "react-icons/fc";
import { useCartStore } from "../../../../stores/CartStore";

export const OrderItem = (props: ProductInfo) => {
  const quantity = useCartStore((state) => {
    const itemIdx = state.findOrderItemIdx(props.id);
    return state.orderItems[itemIdx].quantity;
  });
  const modifyOrderItem = useCartStore((state) => state.modifyOrderItem);
  const calculateTotalPrice = useCartStore(
    (state) => state.calculateTotalPrice
  );

  const handleQtyChange = (newQty: number) => {
    if (newQty < 1) return;
    modifyOrderItem(props.id, newQty);
    calculateTotalPrice();
  };

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
        min={1}
        onValueChange={(e) => handleQtyChange(e.value as number)}
        showButtons={true}
        buttonLayout="vertical"
        className={styles.quantity}
        incrementButtonClassName={styles.btn}
        decrementButtonClassName={`${styles.btn} ${
          quantity === 1 ? styles.disabled : ""
        }`}
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
