/* eslint-disable @typescript-eslint/no-explicit-any */
import Card from "../Card/Card";

import "./Product.css";

const Product = (props: any) => {
  let shorterDescription = props.desc;
  if (shorterDescription.length > 67) {
    shorterDescription = shorterDescription.substring(0, 50) + "...";
  } else if (shorterDescription.length === 0) {
    shorterDescription = "No description provided";
  }

  return (
    <Card className="product-item">
      <div>
        <img
          src="https://images.unsplash.com/photo-1575936123452-b67c3203c357?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Mnx8aW1hZ2V8ZW58MHx8MHx8fDA%3D&w=1000&q=80"
          alt="asd"
          className="product-item__image"
        />
      </div>
      <div className="product-item__information">
        {/* <h4 className="mb-1">{props.name}</h4>
          <h6 className="mb-1">"{props.category.name}"</h6>
          <h6 className="mt-0 mb-3">${props.price}</h6> */}
        <h3 className="align-center">Arduino</h3>
        <h4 className="product-item__category">Electronics</h4>
        <h4 className="product-item__description">{shorterDescription}</h4>
        <h4 className="align-center">Price: $4.00</h4>
        <div>
          <button
            className="product-item__btn"
            style={{ width: "100%" }}
            // onClick={}
          >
            Buy
          </button>
        </div>
      </div>
    </Card>
  );
};

export default Product;
