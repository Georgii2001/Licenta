import React from "react";
import styles from "../../../../css/UserDetails.module.css";

const UserInfoPage = ({ user }) => {
    return (
        <>
            <div className={styles.user_info_page_main}>
                <div className={styles.user_property}> <b>Username:</b> {user.username} </div>
                <div className={styles.user_property}> <b>Full Name:</b> {user.displayName} </div>
                <div className={styles.user_property}> <b>Email:</b> {user.email}</div>
                <div className={styles.user_property}> <b>Gender:</b> {user.gender} </div>
            </div>
            <br></br>
        </>
    );
};

export default UserInfoPage;