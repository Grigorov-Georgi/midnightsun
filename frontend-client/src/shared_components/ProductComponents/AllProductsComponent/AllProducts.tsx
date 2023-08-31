import { useQuery } from "@tanstack/react-query";
import { ProductCard } from "../Product/ProductCard";
import { Paginator, PaginatorPageChangeEvent } from "primereact/paginator";
import styles from "./AllProducts.module.scss";
import { getProductsFromPage } from "../../../services/ProductService";
import { useState } from "react";

export interface ProductFullInfo {
  id: number;
  name: string;
  price: number;
  description: string;
  rating: number;
}

export const AllProducts = () => {
  const [first, setFirst] = useState<number>(0);
  const [currentPage, setCurrentPage] = useState<number>(0);
  const [totalItems, setTotalItems] = useState<number>(0);

  const productsQuery = useQuery({
    queryKey: ["pageProducts", currentPage],
    queryFn: () => getProductsFromPage(currentPage),
  });

  const getProducts = (): JSX.Element[] => {
    let allProducts: ProductFullInfo[] = [];
    if (productsQuery.status === "success") {
      if (productsQuery.data.totalItems !== totalItems) {
        setTotalItems(productsQuery.data.totalItems);
      }
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
      return <ProductCard key={`${product.id}`} allInfo={product} />;
    });
    return products;
  };

  const handlePageChange = (ev: PaginatorPageChangeEvent) => {
    setFirst(ev.first);
    setCurrentPage(ev.page);
    window.scrollTo(0, 0); // go back to the top
  };

  return (
    <div className={styles.page}>
      <div className={styles.grid}>{getProducts()}</div>
      <Paginator
        className={styles.paginator}
        first={first}
        rows={20} // Elements per page
        totalRecords={totalItems} // Count of all elements
        onPageChange={(ev) => handlePageChange(ev)}
      />
    </div>
  );
};
