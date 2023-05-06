import React from "react";
import styles from "../../../../../css/UserDetails.module.css";

const UserInfoPage = ({ user }) => {
    return (
        <>
            <div className={styles.user_info_page_main}>
                <div className={styles.user_property}> Username: {user.username} </div>
                <div className={styles.user_property}> Email: {user.email}</div>
                <div className={styles.user_property}> Gender: {user.gender} </div>
            </div>
            <br></br>
        </>
    );
};

export default UserInfoPage;