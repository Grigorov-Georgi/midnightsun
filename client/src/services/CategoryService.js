import axios from "axios";

const API_BASE_URL = "http://localhost:8082/api/categories/";

export const getAllCategories = () => {
  return axios.get(`${API_BASE_URL}`);
};
