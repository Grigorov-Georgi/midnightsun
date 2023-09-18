import style from "./Home.module.scss";
import img1 from "../../assets/summer_sale.png";
import img2 from "../../assets/syber_sale.png";
import img3 from "../../assets/telescope_sale.png";
import ImageCarousel from "../../shared_components/ImageCarousel/ImageCarousel";
import { useQuery } from "@tanstack/react-query";
import { getTopProducts } from "../../services/ProductService";
import { ProductFullInfo } from "../../types/FullProductInfo";
import { ProductCard } from "../../shared_components/ProductComponents/ProductCard/ProductCard";

const Home = () => {
  const topProductsQuerry = useQuery({
    queryKey: ["topProducts"],
    queryFn: getTopProducts,
  });

  const generateTopProductCards = (): JSX.Element[] => {
    let topProducts: ProductFullInfo[] = [];
    if (topProductsQuerry.status === "success") {
      // eslint-disable-next-line @typescript-eslint/no-explicit-any
      topProducts = topProductsQuerry.data.map((product: any) => {
        return {
          id: product.id,
          name: product.name,
          price: product.price,
          description: product.description,
          rating: product.ratingScore,
        };
      });
    }
    return topProducts.map((productInfo) => (
      <ProductCard key={`key-${productInfo.id}`} allInfo={productInfo} />
    ));
  };

  return (
    <div className={style.homePage}>
      <h1>Welcome to Ecart!</h1>
      <ImageCarousel imgSrcs={[img1, img2, img3]} />
      <h2>Our users recommend: </h2>
      <div className={style.topGrid}>{generateTopProductCards()}</div>
    </div>
  );
};

export default Home;
