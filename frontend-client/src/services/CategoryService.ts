/* eslint-disable @typescript-eslint/no-explicit-any */
import axios from "axios";

const API_HOST = import.meta.env.VITE_PRODUCT_SERVICE_HOST;
const API_SERVICE_PREFIX = import.meta.env.VITE_PRODUCT_SERVICE_PREFIX;
const API_BASE_URL = API_HOST + API_SERVICE_PREFIX + "/api/categories/";

export const getAllCategories = (isAuthenticated: boolean) => {
  if (!isAuthenticated) return [];
  return axios
    .get(`${API_BASE_URL}`)
    .then((response) => {
      return response.data;
    })
    .catch((err) => {
      return err;
    });
};
