import style from "./Home.module.scss";

const Home = () => {
  return (
    <div className={style.homePage}>
      <h1>Welcome to Ecart!</h1>
      <h2>Carusel goes here</h2>
      <h2>Our users recommend: </h2>
      <h3>Top products</h3>
    </div>
  );
};

export default Home;
