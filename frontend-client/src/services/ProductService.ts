/* eslint-disable @typescript-eslint/no-explicit-any */
import axios from "axios";
import { NewProduct } from "../types/NewProduct";

const API_HOST = import.meta.env.VITE_PRODUCT_SERVICE_HOST;
const API_SERVICE_PREFIX = import.meta.env.VITE_PRODUCT_SERVICE_PREFIX;
const API_BASE_URL = API_HOST + API_SERVICE_PREFIX + "/api/products";

export async function getProductsFromPage(
  pageIdx: number
): Promise<{ content: any; totalItems: number }> {
  return axios
    .get(`${API_BASE_URL}?page=${pageIdx}&size=20`)
    .then((response) => {
      return {
        content: response.data.content,
        totalItems: response.data.totalElements,
      };
    })
    .catch((err) => {
      console.log(err);
      return { content: [], totalItems: 0 };
    });
}

export const createProduct = (newProductData: NewProduct) => {
  return axios
    .post(`${API_BASE_URL}`, newProductData)
    .then((response) => {
      console.log(response);
      return response;
    })
    .catch((err) => err);
};

export const getTopProducts = () => {
  return axios
    .get(`${API_BASE_URL}/top?n=6`)
    .then((response) => response.data)
    .catch((err) => console.log(err));
};
