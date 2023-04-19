import React from "react";
import BackgroundHome from "../../../fragments/background/BackgroundHome";
import dancingImg from "../../../../../img/2.jpg";
import styles from "../../../../../css/Account.module.css";
import layout from "../../../../../css/UserHome.module.css";
import "react-confirm-alert/src/react-confirm-alert.css";
import UserInterests from "./modules/UserInterests"
import UserInfo from "./modules/UserInfo";
import UserMedia from "./modules/UserMedia";

const AccountUser = () => {

  return (
    <>
      <main className={layout.hobbie_main}>
        <section className={layout.hobbie_container_home}>
          <section className={styles.account_container}>
            <UserMedia />
            <UserInterests />
            <UserInfo />
          </section>

          <BackgroundHome />
        </section>
      </main>
    </>
  );
};

export default AccountUser;
