import { Button } from "primereact/button";
import styles from "./ProductCard.module.css";
import placeholderImg from "../../../assets/2-2-space-free-png-image.png";
import { useCartStore } from "../../../stores/CartStore";
import { IOrderItem } from "../../../types/IOrderItem";
import { ProductFullInfo } from "../AllProductsComponent/AllProducts";

export const ProductCard = (props: { allInfo: ProductFullInfo }) => {
  const { allInfo } = props;
  const addOrderItem = useCartStore((state) => state.addOrderItem);

  const handleBuyClick = () => {
    const newOrderItem: IOrderItem = {
      info: { id: allInfo.id, name: allInfo.name, price: allInfo.price },
      quantity: 1,
    };
    addOrderItem(newOrderItem);
  };

  return (
    <div className={styles.card}>
      <img src={placeholderImg} alt="Image of name" className={styles.image} />
      <div className={styles.nameReviewContainer}>
        <div className={styles.productName}>{allInfo.name}</div>
        <div>{allInfo.rating}/5</div>
      </div>
      <div className={styles.descriptionContainer}>{allInfo.description}</div>
      <div className={styles.price}>{allInfo.price}$</div>
      <Button
        label="Buy"
        className={styles.buyBtn}
        onClick={() => handleBuyClick()}
      />
    </div>
  );
};
