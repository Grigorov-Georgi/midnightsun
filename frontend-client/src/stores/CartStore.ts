import { create } from "zustand";
import { persist, createJSONStorage } from "zustand/middleware";
import { IOrderItem } from "../types/IOrderItem";
import { NewOrderItem } from "../types/NewOrderItem";

// const dummyProducts: IOrderItem[] = [
//   { info: { id: 1, name: "Clone trooper", price: 15 }, quantity: 1 },
//   { info: { id: 2, name: "Newest Junji Ito manga", price: 20 }, quantity: 1 },
//   { info: { id: 3, name: "Halo 10", price: 60 }, quantity: 1 },
//   {
//     info: { id: 4, name: "Space Marines Tactical Squad", price: 30 },
//     quantity: 1,
//   },
//   { info: { id: 5, name: "HG Gundam Barbatos", price: 25 }, quantity: 1 },
// ];

interface CartStore {
  orderItems: IOrderItem[];
  totalPrice: number;
  addOrderItem: (newItem: IOrderItem) => void;
  removeOrderItem: (productId: string) => void;
  modifyOrderItem: (productId: string, newQty: number) => void;
  generateItemsForOrder: () => NewOrderItem[];
  calculateTotalPrice: () => void;
  resetStore: () => void;
}

export const useCartStore = create<CartStore>()(
  persist(
    (set, get) => ({
      orderItems: [],
      totalPrice: 0,

      addOrderItem: (newItem: IOrderItem) => {
        const allItems: IOrderItem[] = get().orderItems;
        allItems.push(newItem);
        set(() => ({ orderItems: [...allItems] }));
        get().calculateTotalPrice();
      },

      removeOrderItem: (productId: string) => {
        const allItems = get().orderItems;
        const idxToDelete = allItems.findIndex(
          (item) => item.info.id === productId
        );
        allItems.splice(idxToDelete, 1);
        set(() => ({ orderItems: [...allItems] }));
        get().calculateTotalPrice();
      },

      // Incr/decr quantity and recalculate pr
      modifyOrderItem: (productId: string, newQty: number) => {
        const allItems = get().orderItems;
        const selectedItem = allItems.find(
          (item) => item.info.id === productId
        );
        if (!selectedItem) return;
        selectedItem.quantity = newQty;
        set(() => ({ orderItems: [...allItems] }));
        get().calculateTotalPrice();
      },

      // Needed instead of a reaction (no native computed)
      calculateTotalPrice: () => {
        const currentItems: IOrderItem[] = get().orderItems;
        let totalPrice = 0;
        currentItems.forEach(
          (item) => (totalPrice += item.info.price * item.quantity)
        );
        set(() => ({ totalPrice: totalPrice }));
      },

      generateItemsForOrder: () => {
        const currentItems: IOrderItem[] = get().orderItems;
        return currentItems.map(
          (orderItem): NewOrderItem => ({
            productId: orderItem.info.id,
            quantity: orderItem.quantity,
          })
        );
      },

      resetStore: () => {
        set(() => ({ totalPrice: 0, orderItems: [] }));
      },
    }),
    { name: "cart-storage", storage: createJSONStorage(() => sessionStorage) }
  )
);
