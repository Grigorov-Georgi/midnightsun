import { Button } from "primereact/button";
import styles from "./ProductCard.module.css";
import placeholderImg from "../../../assets/2-2-space-free-png-image.png";
import { useCartStore } from "../../../stores/CartStore";
import { IOrderItem } from "../../../types/IOrderItem";

export const ProductCard = () => {
  const addOrderItem = useCartStore((state) => state.addOrderItem);

  const handleBuyClick = () => {
    const newOrderItem: IOrderItem = {
      info: { id: 100, name: "Lucky galaxy", price: 777 },
      quantity: 1,
    };
    addOrderItem(newOrderItem);
  };

  return (
    <div className={styles.card}>
      <img src={placeholderImg} alt="Image of name" className={styles.image} />
      <div className={styles.nameReviewContainer}>
        <div className={styles.productName}>Lucky galaxy</div>
        <div>3.5/5</div>
      </div>
      <div className={styles.descriptionContainer}>
        Lorem ipsum dolor sit amet, consectetur adipiscing elit. In nec justo
        mollis, cursus quam eget, efficitur leo. Donec tristique odio non augue
        porttitor placerat. Etiam sodales et lacus in facilisis. Duis vehicula
        augue eget consectetur maximus. Nam ut sem id ipsum rhoncus rutrum. Cras
        at libero et sapien pretium mollis. Proin hendrerit ultricies magna
      </div>
      <div className={styles.price}>$777</div>
      <Button
        label="Buy"
        className={styles.buyBtn}
        onClick={() => handleBuyClick()}
      />
    </div>
  );
};
