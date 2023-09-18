import { useQuery } from "@tanstack/react-query";
import { Paginator, PaginatorPageChangeEvent } from "primereact/paginator";
import styles from "./AllProducts.module.scss";
import { useEffect, useState } from "react";
import { getProductsFromPage } from "../../services/ProductService";
import { ProductCard } from "../../shared_components/ProductComponents/ProductCard/ProductCard";
import { ProductFullInfo } from "../../types/FullProductInfo";
import { useSearchParams } from "react-router-dom";

export const AllProducts = () => {
  const [first, setFirst] = useState<number>(0);
  const [totalItems, setTotalItems] = useState<number>(0);
  const [searchParams, setSearchParams] = useSearchParams();
  const currentPage = searchParams.get("page");

  const productsQuery = useQuery({
    queryKey: ["pageProducts", currentPage],
    queryFn: () => getProductsFromPage(Number(currentPage)),
  });

  useEffect(() => {
    setSearchParams({ page: "1" });
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

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
    const page = ev.page + 1;
    setSearchParams({ page: page.toString() });
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
