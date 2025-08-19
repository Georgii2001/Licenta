import { React, useState, useLayoutEffect } from "react";
import BackgroundHome from "../../fragments/background/BackgroundHome";
import styles from "../../../../css/Account.module.css";
import layout from "../../../../css/Background.module.css";
import "react-confirm-alert/src/react-confirm-alert.css";
import UserInterests from "./modules/UserInterests"
import UserInfo from "./modules/UserInfo";
import UserMedia from "./modules/UserMedia";
import UserDataService from "../../../../api/users/UserDataService";
import UserDescription from "./modules/UserDescription";

const AccountUser = () => {

  const [user, setUser] = useState([]);

  useLayoutEffect(() => {
    let unmounted = false;

    UserDataService().then((response) => {
      if (!unmounted) {
        setUser(response.data);
      }
    });
    return () => {
      unmounted = true;
    };
  }, []);

  const refreshUserData = () => {
    UserDataService().then((response) => {
      setUser(response.data);
    });
  }

  return (
    <>
      <BackgroundHome />
      <main className={layout.main_area}>
        <section className={layout.main_area_container_home}>
          <section className={styles.account_container}>
            <UserMedia userAvatars={user.avatarFiles} refreshUserData={refreshUserData} />
            <UserInterests id={user.id} userInterests={user.interests} refreshUserData={refreshUserData} />
            <UserDescription email={user.email} description={user.description} refreshUserData={refreshUserData} />
            <UserInfo user={user} />
          </section>
        </section>
      </main>
    </>
  );
};

export default AccountUser;
