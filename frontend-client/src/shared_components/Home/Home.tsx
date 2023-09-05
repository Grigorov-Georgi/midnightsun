import ImageCarousel from "../ImageCarousel/ImageCarousel";
import style from "./Home.module.scss";
import img1 from "../../assets/2-2-space-free-png-image.png";
import img2 from "../../assets/space-planets.png";
import img3 from "../../assets/stock_3.png";

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
