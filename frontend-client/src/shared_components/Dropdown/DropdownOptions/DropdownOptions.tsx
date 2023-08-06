import styles from "./DropdownOptions.module.css";

interface DropdownOptionsProps {
  options: { name: string; path: string }[];
}

export const DropdownOptions = (props: DropdownOptionsProps) => {
  const { options } = props;
  return (
    <div className={styles.dpdMenu}>
      <ul className={styles.optionsList}>
        {options.map((option) => (
          <li className={styles.option}>
            <a className={styles.optionLink} href={option.path}>
              {option.name}
            </a>
          </li>
        ))}
      </ul>
    </div>
  );
};
