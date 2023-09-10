/* eslint-disable @typescript-eslint/no-explicit-any */
import axios from "axios";
import { NewProduct } from "../types/NewProduct";

const API_BASE_URL = "http://localhost:8082/api/products";

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
