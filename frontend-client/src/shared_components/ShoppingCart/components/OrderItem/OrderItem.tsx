import styles from "./OrderItem.module.scss";
import placeholderImage from "../../../../assets/2-2-space-free-png-image.png";
import { ProductInfo } from "../../../../types/ProductInfo";
import { InputNumber } from "primereact/inputnumber";
import { useEffect, useState } from "react";
import { FcEmptyTrash } from "react-icons/fc";
import { useCartStore } from "../../../../stores/CartStore";

export const OrderItem = (props: ProductInfo) => {
  const itemInfo = useCartStore((state) => {
    const itemIdx = state.findOrderItemIdx(props.id);
    return state.orderItems[itemIdx];
  });
  const modifyOrderItem = useCartStore((state) => state.modifyOrderItem);
  const calculateTotalPrice = useCartStore(
    (state) => state.calculateTotalPrice
  );
  const [quantity, setQuantity] = useState<number>(0);

  useEffect(() => {
    setQuantity(itemInfo.quantity);
  }, [itemInfo]);

  const handleQtyChange = (newQty: number) => {
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
        onValueChange={(e) => handleQtyChange(e.value as number)}
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
