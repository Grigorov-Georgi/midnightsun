import { Galleria } from "primereact/galleria";
import styles from "./ImageCarousel.module.scss";

interface ImageCarouselProps {
  imgSrcs: string[];
}

const ImageCarousel = (props: ImageCarouselProps) => {
  const { imgSrcs } = props;

  const itemTemplate = (imageSrc: string) => {
    return <img src={imageSrc} className={styles.image} />;
  };
  return (
    <div>
      <Galleria
        value={imgSrcs}
        item={itemTemplate}
        showItemNavigators={true}
        showThumbnails={false}
        numVisible={1}
        circular
        style={{ maxWidth: "1000px" }}
        className={styles.carousel}
      />
    </div>
  );
};

export default ImageCarousel;
