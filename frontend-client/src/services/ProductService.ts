/* eslint-disable @typescript-eslint/no-explicit-any */
import axios from "axios";
import { NewProduct } from "../types/NewProduct";

const API_BASE_URL = "http://localhost:8082/api/products/";

// export async function getAllProducts() {
//   const response = await fetch(API_BASE_URL);
//   const data = await response.json();
//   return data;
//   // return axios.get(`${API_BASE_URL}`);
// }

export async function getAllProducts() {
  return axios
    .get(`${API_BASE_URL}`)
    .then((response) => {
      return response.data;
    })
    .catch((err) => {
      return err;
    });
}

export async function getProductById(productId: any) {
  const response = await fetch(`${API_BASE_URL}/${productId}`);
  const data = await response.json();
  return data;
}

// export const getProductById = (productId) => {
//   return axios.get(`${API_BASE_URL}${productId}`);
// };

// export async function createProduct(newProduct: NewProduct) {
//   const response = await fetch(API_BASE_URL, {
//     method: "POST",
//     body: JSON.stringify(newProduct),
//     headers: {
//       "Content-Type": "application/json",
//     },
//   });
//   const data = await response.json();
//   console.log(data);
//   return data;
// }

export const createProduct = (newProductData: NewProduct) => {
  return axios
    .post(`${API_BASE_URL}`, newProductData)
    .then((response) => {
      console.log(response);
      return response;
    })
    .catch((err) => err);
};

export const updateProduct = (updatedProductData: any) => {
  return axios.put(`${API_BASE_URL}`, updatedProductData);
};

export const deleteProduct = (productId: any) => {
  return axios.delete(`${API_BASE_URL}${productId}`);
};
