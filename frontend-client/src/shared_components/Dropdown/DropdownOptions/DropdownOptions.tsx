import { Link } from "react-router-dom";
import styles from "./DropdownOptions.module.scss";

interface DropdownOptionsProps {
  options: { name: string; path: string }[];
}

export const DropdownOptions = (props: DropdownOptionsProps) => {
  const { options } = props;
  return (
    <div className={styles.dpdMenu}>
      <ul className={styles.optionsList}>
        {options.map((option) => (
          <li key={`dpd-${option.name}`} className={styles.option}>
            <Link className={styles.optionLink} to={option.path}>
              {option.name}
            </Link>
          </li>
        ))}
      </ul>
    </div>
  );
};
