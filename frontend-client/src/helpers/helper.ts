export const findIdxFromId = (allIds: number[], productId: number): number => {
  return allIds.findIndex((id) => id === productId);
};
