import { ProductFullInfo } from "../types/FullProductInfo";

/* eslint-disable @typescript-eslint/no-explicit-any */
export const findIdxFromId = (allIds: number[], productId: number): number => {
  return allIds.findIndex((id) => id === productId);
};

export const formatProductFullInfo = (data: any): ProductFullInfo[] => {
  return data.map(
    (product: any): ProductFullInfo => ({
      id: product.id,
      name: product.name,
      price: product.price,
      description: product.description,
      rating: product.ratingScore,
    })
  );
};
