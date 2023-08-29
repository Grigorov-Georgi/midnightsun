import { useQuery } from "@tanstack/react-query";
import { ProductCard } from "../Product/ProductCard";
import { Paginator, PaginatorPageChangeEvent } from "primereact/paginator";
import styles from "./AllProducts.module.scss";
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

  const handlePageChange = (ev: PaginatorPageChangeEvent) => {
    console.log("Page change ev: ", ev);
  };

  return (
    <div className={styles.page}>
      <div className={styles.grid}>{getProducts()}</div>
      <Paginator
        className={styles.paginator}
        first={0}
        rows={10}
        totalRecords={120}
        onPageChange={(ev) => handlePageChange(ev)}
      />
    </div>
  );
};
