import React from "react";
import Card from '../Card/Card'
import Product from "./Product";

import "./ProductsGrid.css";


const ProductsGrid = (props) => {
  return (
    <Card className='products-grid'>
        <Product />
        <Product />
        <Product />
        <Product />
        <Product />
    </Card>
  );
};

export default ProductsGrid;