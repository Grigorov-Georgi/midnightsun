import "./Card.css";

// eslint-disable-next-line @typescript-eslint/no-explicit-any
const Card = (props: any) => {
  const classes = "card-ui " + props.className;

  return <div className={classes}>{props.children}</div>;
};

export default Card;
