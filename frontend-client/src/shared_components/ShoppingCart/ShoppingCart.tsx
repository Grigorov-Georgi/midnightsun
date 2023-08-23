import { Button } from "primereact/button";
import OrderInfo from "./components/OrderInfo/OrderInfo";
import OrderItem from "./components/OrderItem/OrderItem";
import styles from "./ShoppingCart.module.scss";
import { ProductInfo } from "../../types/ProductInfo";

const dummyProducts: ProductInfo[] = [
  { id: 1, name: "Clone trooper", price: 15 },
  { id: 2, name: "Newest Junji Ito manga", price: 20 },
  { id: 3, name: "Halo 10", price: 60 },
  { id: 4, name: "Space Marines Tactical Squad", price: 30 },
  { id: 5, name: "HG Gundam Barbatos", price: 25 },
];

export const ShoppingCart = () => {
  const generateDummyData = (): JSX.Element[] => {
    const data: JSX.Element[] = [];
    dummyProducts.forEach((product) =>
      data.push(
        <OrderItem
          key={`order-item-${product.id}`}
          id={product.id}
          name={product.name}
          price={product.price}
        />
      )
    );
    return data;
  };

  return (
    <div className={styles.shoppingCart}>
      <div className={styles.orderItemsList}>{generateDummyData()}</div>
      <div className={styles.infoSection}>
        <OrderInfo />
        <Button label={"Order"} className={styles.orderBtn} />
      </div>
    </div>
  );
};

export default ShoppingCart;
