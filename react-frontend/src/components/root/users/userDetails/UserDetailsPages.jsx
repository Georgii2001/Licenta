import React from "react";
import styles from "../../../../css/UserDetails.module.css";
import { useState, useLayoutEffect } from "react";
import "react-confirm-alert/src/react-confirm-alert.css";
import UserByIdDataService from "../../../../api/users/UserByIdDataService";
import UserDescriptionPage from "../commonPages/UserDescriptionPage";
import UserMediaPage from "../commonPages/UserMediaPage";

const UserDetailsPages = ({ id }) => {

  const [user, setUser] = useState([]);
  const [currentPage, setCurrentPage] = useState("Media");

  useLayoutEffect(() => {
    let unmounted = false;

    UserByIdDataService(id).then((response) => {
      if (!unmounted) {
        setUser(response.data);
      }
    });
    return () => {
      unmounted = true;
    };
  }, []);

  const changePage = (page) => {
    setCurrentPage(page);
  };

  return (
    <>
      <section className={styles.user_details_container}>
        <div className={styles.user_details_content}>
          <div className={styles.username}>
            {user.displayName}
          </div>
          <br></br>
          <article className={styles.user_details_pages_horizontal}>
            <span onClick={() => changePage("Media")} className={currentPage === "Media" ? styles.page_title_active : styles.page_title}>
              Media
            </span>
            <span onClick={() => changePage("Description")} className={currentPage === "Description" ? styles.page_title_active : styles.page_title}>
              Description
            </span>
          </article>
          <br></br>

          {currentPage === "Media" && <UserMediaPage avatars={user.avatarFiles} />}
          {currentPage === "Description" && <UserDescriptionPage description={user.description} interests={user.interests} />}
        </div>
      </section>
    </>
  );
};

export default UserDetailsPages;
