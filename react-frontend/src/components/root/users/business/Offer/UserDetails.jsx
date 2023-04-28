import React from "react";
import BackgroundHome from "../../../fragments/background/BackgroundHome";
import HobbiePages from "./HobbiePages";
import { useMediaQuery } from "beautiful-react-hooks";
import styles from "../../../../../css/Hobbie.module.css";
import "react-confirm-alert/src/react-confirm-alert.css";

const UserDetails = () => {
  const isColumnBasedSmall = useMediaQuery("(max-width: 900px)");
  console.log(isColumnBasedSmall);

  return (
    <>
      <main
        className={
          isColumnBasedSmall ? styles.hobbie_main_small : styles.hobbie_main
        }
      >
        <HobbiePages />
        <BackgroundHome />
      </main>
    </>
  );
};

export default UserDetails;
