import React from "react";
import styles from "../../../../../../css/Account.module.css";

const UserDescription = ({ description, refreshUserData }) => {

    return (
        <>
            <div className={styles.account_content}>
                <span className={styles.account_title}>
                    <b>Description</b>
                </span>
                <hr className={styles.account_hr}></hr>
                <br></br>
            </div>
        </>
    );
};

export default UserDescription;