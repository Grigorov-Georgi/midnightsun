import style from "./Home.module.scss";
import img1 from "../../assets/summer_sale.png";
import img2 from "../../assets/syber_sale.png";
import img3 from "../../assets/telescope_sale.png";
import ImageCarousel from "../../shared_components/ImageCarousel/ImageCarousel";

const Home = () => {
  return (
    <div className={style.homePage}>
      <h1>Welcome to Ecart!</h1>
      <ImageCarousel imgSrcs={[img1, img2, img3]} />
      <h2>Our users recommend: </h2>
      <h3>Top products</h3>
    </div>
  );
};

export default Home;
