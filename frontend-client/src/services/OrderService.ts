import axios from "axios";
import { NewOrderItem } from "../types/NewOrderItem";

const API_BASE_URL = "http://localhost:8081/api/orders";

export const createNewOrder = (newOrderItems: NewOrderItem[]) => {
  const dto = { orderItems: newOrderItems };
  return axios
    .post(`${API_BASE_URL}`, dto)
    .then((response) => response)
    .catch((err) => err);
};
