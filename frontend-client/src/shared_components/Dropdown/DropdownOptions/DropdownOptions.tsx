import { Link } from "react-router-dom";
import styles from "./DropdownOptions.module.scss";

interface DropdownOptionsProps {
  options: { name: string; path: string }[];
  handleDropdownClose: () => void;
}

export const DropdownOptions = (props: DropdownOptionsProps) => {
  const { options, handleDropdownClose } = props;
  return (
    <div
      className={styles.dpdMenu}
      tabIndex={1}
      autoFocus={true}
      onBlur={() => console.log("blur event")}
    >
      <ul className={styles.optionsList}>
        {options.map((option) => (
          <li key={`dpd-${option.name}`} className={styles.option}>
            <Link
              className={styles.optionLink}
              to={option.path}
              onClick={handleDropdownClose}
            >
              {option.name}
            </Link>
          </li>
        ))}
      </ul>
    </div>
  );
};
