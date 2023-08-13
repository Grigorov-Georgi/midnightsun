import { ProductCard } from "../Product/ProductCard";
import styles from "./AllProducts.module.css";

export const AllProducts = () => {
  const getDummyProducts = (): JSX.Element[] => {
    const products: JSX.Element[] = [];
    for (let index = 0; index < 10; index++) {
      products.push(<ProductCard />);
    }
    return products;
  };
  return (
    <div className={styles.page}>
      <div className={styles.grid}>{getDummyProducts()}</div>
    </div>
  );
};
