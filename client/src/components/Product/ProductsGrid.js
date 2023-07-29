import React from "react";
import Card from '../Card/Card'
import Product from "./Product";

import "./ProductsGrid.css";


const ProductsGrid = (props) => {
  return (
    <Card className='products-grid'>
        <Product desc="sdf"/>
        <Product desc="asfdasf fasdf asdf dsf asdf asf sdf FASD FSAFSAF  sf sf sf ads"/>
        <Product desc="Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic"/>
        <Product desc=""/>
        <Product desc="Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic"/>
    </Card>
  );
};

export default ProductsGrid;