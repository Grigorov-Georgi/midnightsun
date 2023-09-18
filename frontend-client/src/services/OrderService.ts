import axios from "axios";
import { NewOrderItem } from "../types/NewOrderItem";

const API_HOST = import.meta.env.VITE_ORDER_SERVICE_HOST;
const API_SERVICE_PREFIX = import.meta.env.VITE_ORDER_SERVICE_PREFIX;
const API_BASE_URL = API_HOST + API_SERVICE_PREFIX + "/api/orders";

export const createNewOrder = (newOrderItems: NewOrderItem[]) => {
  const dto = { orderItems: newOrderItems };
  return axios
    .post(`${API_BASE_URL}`, dto)
    .then((response) => response)
    .catch((err) => err);
};
