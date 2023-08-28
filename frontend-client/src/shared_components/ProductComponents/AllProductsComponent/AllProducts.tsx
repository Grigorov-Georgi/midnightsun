import { useQuery } from "@tanstack/react-query";
import { ProductCard } from "../Product/ProductCard";
import styles from "./AllProducts.module.css";
import { getAllProducts } from "../../../services/ProductService";

export interface ProductFullInfo {
  id: number;
  name: string;
  price: number;
  description: string;
  rating: number;
}

export const AllProducts = () => {
  const productsQuery = useQuery({
    queryKey: ["products"],
    queryFn: getAllProducts,
  });

  const getProducts = (): JSX.Element[] => {
    let allProducts: ProductFullInfo[] = [];
    if (productsQuery.status === "success") {
      // eslint-disable-next-line @typescript-eslint/no-explicit-any
      allProducts = productsQuery.data.content.map((product: any) => {
        return {
          id: product.id,
          name: product.name,
          price: product.price,
          description: product.description,
          rating: product.ratingScore,
        };
      });
    }
    let products: JSX.Element[] = [];
    products = allProducts.map((product) => {
      return <ProductCard key={`product-${product.id}`} allInfo={product} />;
    });
    return products;
  };

  return (
    <div className={styles.page}>
      <div className={styles.grid}>{getProducts()}</div>
    </div>
  );
};
