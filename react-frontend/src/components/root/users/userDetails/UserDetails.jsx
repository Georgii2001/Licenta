import React from "react";
import BackgroundHome from "../../fragments/background/BackgroundHome";
import UserDetailsPages from "./UserDetailsPages";
import { useMediaQuery } from "beautiful-react-hooks";
import styles from "../../../../css/UserDetails.module.css";
import "react-confirm-alert/src/react-confirm-alert.css";
import { useLocation } from "react-router-dom";

const UserDetails = () => {
  
  const isColumnBasedSmall = useMediaQuery("(max-width: 900px)");
  const location = useLocation();

  return (
    <>
      <main
        className={
          isColumnBasedSmall ? styles.hobbie_main_small : styles.hobbie_main
        }
      >
        <UserDetailsPages id={location.state.id}/>
        <BackgroundHome />
      </main>
    </>
  );
};

export default UserDetails;
