/* eslint-disable @typescript-eslint/no-explicit-any */
import React from "react";
import Card from "../Card/Card";

import "./ProductDetails.css";

const ProductDetails = () => {
  return (
    <Card className="product-details">
      <div className="product-details__image">
        <img
          src="https://images.unsplash.com/photo-1575936123452-b67c3203c357?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Mnx8aW1hZ2V8ZW58MHx8MHx8fDA%3D&w=1000&q=80"
          alt="asd"
          style={{ height: "inherit" }}
        />
      </div>
      <div className="product-details__information">
        {/* <h4 className="mb-1">{props.name}</h4>
          <h6 className="mb-1">"{props.category.name}"</h6>
          <h6 className="mt-0 mb-3">${props.price}</h6> */}
        <h3 className="align-center">Arduino</h3>
        <h4 className="product-details__category">Electronics</h4>
        <h4 className="product-details__description">
          Lorem Ipsum has been the industry's standard dummy text ever since the
          1500s, when an unknown printer took a galley of type and scrambled it
          to make a type specimen book. It has survived not only five centuries,
          but also the leap into electronic
        </h4>
        <h4 className="align-center">Price: $4.00</h4>
        <div>
          <button
            className="product-details__btn"
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

export default ProductDetails;
