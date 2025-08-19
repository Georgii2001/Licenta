import React from "react";
import styles from "../../../../../css/Logo.module.css";
import logo from "../../../../../img/logo.svg";
import AuthenticationService from "../../../../../api/authentication/AuthenticationService";
import { useNavigate } from "react-router-dom";
import { NavLink } from "react-router-dom";

const Logo = () => {
  const isUserLoggedIn = AuthenticationService.isUserLoggedIn();
  const navigate = useNavigate();

  return (
    <section className={styles.logo_container}>
      {!isUserLoggedIn && (
        <NavLink onClick={() => navigate("/")} to="/" >
          <img className={styles.imgHeader} src={logo} alt="logo" />
        </NavLink>
      )}

      {isUserLoggedIn && (
        <NavLink onClick={() => navigate("/user-home")} to="/user-home" >
          <img className={styles.imgHeader} src={logo} alt="logo" />
        </NavLink>
      )}
      {isUserLoggedIn && (
        <NavLink onClick={() => navigate("/user-home")} to="/user-home" >
          <h4 className={styles.logo}></h4>
        </NavLink>
      )}
    </section>
  );
};

export default Logo;
