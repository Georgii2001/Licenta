import React from "react";
import styles from "../../../../css/Background.module.css";
import blueImg from "../../../../img/blueImg.png";

const BackgroundHome = () => {
  return (
    <>
      <img className={styles.backgroundImg} src={blueImg} alt="backgroundImg" />
    </>
  );
};

export default BackgroundHome;
