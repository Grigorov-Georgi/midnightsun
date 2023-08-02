import "./Footer.css";
import github_logo from "../../../assets/github_logo.png";
import linkedin_logo from "../../../assets//linkedin_logo.png";

const Footer = () => {
  return (
    <footer className="footer">
      <div>
        <h3>© 2023 Copyright: Georgi Grigorov</h3>
        <a
          className="btn linkedin_btn"
          href="https://www.linkedin.com/in/georgi-grigorov-ba82271b5/"
          role="button"
        >
          <img src={linkedin_logo} alt="linked_image" height="40" width="40" />
        </a>
        <a
          className="btn github_btn"
          href="https://github.com/Grigorov-Georgi"
          role="button"
        >
          <img src={github_logo} alt="github_image" height="35" width="35" />
        </a>
      </div>
    </footer>
  );
};

export default Footer;
