import styles from "./ErrorComponent.module.css";

export const ErrorComponent = () => {
  // TODO -> readjust responsivness later
  return (
    <div className={styles.errorDiv}>
      <h1>Ooops!</h1>
      <h2>Your page can not be found! Try a different one!</h2>
      <h1>404</h1>
    </div>
  );
};
