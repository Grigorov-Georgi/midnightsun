/* eslint-disable react-hooks/exhaustive-deps */
import { Button } from "primereact/button";
import styles from "./ShoppingCart.module.scss";
import { useCartStore } from "../../stores/CartStore";
import { useAuth0 } from "@auth0/auth0-react";
import { useMutation } from "@tanstack/react-query";
import { NewOrderItem } from "../../types/NewOrderItem";
import { createNewOrder } from "../../services/OrderService";
import OrderItem from "../../shared_components/ShoppingCart/OrderItem/OrderItem";
import OrderInfo from "../../shared_components/ShoppingCart/OrderInfo/OrderInfo";

export const ShoppingCart = () => {
  const itemsInCart = useCartStore((state) => state.orderItems);
  const getOrderItems = useCartStore((state) => state.generateItemsForOrder);
  const resetStore = useCartStore((state) => state.resetStore);
  const { isAuthenticated } = useAuth0();

  const newOrderMutation = useMutation({
    mutationFn: (newOrderItems: NewOrderItem[]) =>
      createNewOrder(newOrderItems),
  });

  const generateOrderItems = (): JSX.Element[] => {
    const data: JSX.Element[] = [];
    itemsInCart.forEach((product) =>
      data.push(
        <OrderItem
          key={`order-item-${product.info.id}`}
          id={product.info.id}
          name={product.info.name}
          price={product.info.price}
          quantity={product.quantity}
        />
      )
    );
    return data;
  };

  const handleOrderClick = async () => {
    const orderItems: NewOrderItem[] = getOrderItems();
    console.log(orderItems);
    try {
      const result = await newOrderMutation.mutateAsync(orderItems);
      if (result.status === 201) {
        window.alert("Thank you for your purrchase!");
        resetStore();
      }
    } catch (err) {
      console.log("Something went wrong.");
    }
  };

  return (
    <div className={styles.shoppingCart}>
      <div className={styles.orderItemsList}>{generateOrderItems()}</div>
      <div className={styles.infoSection}>
        <OrderInfo />
        <Button
          label={"Order"}
          className={`${styles.orderBtn}`}
          disabled={!isAuthenticated}
          onClick={handleOrderClick}
        />
      </div>
    </div>
  );
};

export default ShoppingCart;
