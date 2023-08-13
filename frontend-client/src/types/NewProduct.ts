export interface NewProduct {
  name: string;
  price: number;
  quantity: number;
  description: string;
  category: { id: number };
}
