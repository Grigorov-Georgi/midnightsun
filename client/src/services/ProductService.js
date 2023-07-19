import axios from "axios";

const API_BASE_URL = "http://localhost:8082/api/products/";

export const getAllProducts = () => {
  return axios.get(`${API_BASE_URL}`);
};

export const getProductById = (productId) => {
  return axios.get(`${API_BASE_URL}${productId}`);
};

export const createProduct = (newProductData) => {
  return axios.post(`${API_BASE_URL}`, newProductData);
};

export const updateProduct = (updatedProductData) => {
  return axios.put(`${API_BASE_URL}`, updatedProductData);
};

export const deleteProduct = (productId) => {
  return axios.delete(`${API_BASE_URL}${productId}`);
};
