import { create } from "zustand";
import { ProductInfo } from "../types/ProductInfo";

export interface OrderItem {
  info: ProductInfo;
  quantity: number;
}

// TODO: Define total price calculations
interface CartStore {
  orderItems: OrderItem[];
  totalPrice: number;
  appendOrderItem: (newItem: OrderItem) => void;
  removeOrderItem: (productId: number) => void;
  modifyOrderItem: (productId: number) => void;
}

export const useCartStore = create<CartStore>((set, get) => ({
  orderItems: [],
  totalPrice: 40000,
  appendOrderItem: (newItem: OrderItem) => {
    const currentItems: OrderItem[] = get().orderItems;
    currentItems.push(newItem);
    set(() => ({ orderItems: currentItems }));
  },
  removeOrderItem: (productId: number) => {
    console.log("remove: ", productId);
  },
  modifyOrderItem: (productId: number) => {
    console.log("modify: "), productId;
  },
}));
