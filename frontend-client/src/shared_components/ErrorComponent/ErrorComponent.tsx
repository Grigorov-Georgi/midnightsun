import styles from "./ErrorComponent.module.css";

export const ErrorComponent = (props: { isPathRelated: boolean }) => {
  const { isPathRelated = false } = props;
  // TODO -> readjust responsivness later
  return (
    <div className={styles.errorDiv}>
      {isPathRelated && (
        <>
          <h1>Ooops!</h1>
          <h2>Your page can not be found! Try a different one!</h2>
          <h1>404</h1>{" "}
        </>
      )}
      {!isPathRelated && (
        <>
          <h1>Ooops!</h1>
          <h2>An internal error has occured</h2>
          <h1>Apologies</h1>
        </>
      )}
    </div>
  );
};
