import { create } from "zustand";
import { IOrderItem } from "../types/IOrderItem";

const dummyProducts: IOrderItem[] = [
  { info: { id: 1, name: "Clone trooper", price: 15 }, quantity: 1 },
  { info: { id: 2, name: "Newest Junji Ito manga", price: 20 }, quantity: 1 },
  { info: { id: 3, name: "Halo 10", price: 60 }, quantity: 1 },
  {
    info: { id: 4, name: "Space Marines Tactical Squad", price: 30 },
    quantity: 1,
  },
  { info: { id: 5, name: "HG Gundam Barbatos", price: 25 }, quantity: 1 },
];

// TODO: Define total price calculations
interface CartStore {
  orderItems: IOrderItem[];
  totalPrice: number;
  appendOrderItem: (newItem: IOrderItem) => void;
  removeOrderItem: (productId: number) => void;
  modifyOrderItem: (productId: number) => void;
}

export const useCartStore = create<CartStore>((set, get) => ({
  orderItems: dummyProducts,
  totalPrice: 40000,
  appendOrderItem: (newItem: IOrderItem) => {
    const currentItems: IOrderItem[] = get().orderItems;
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
