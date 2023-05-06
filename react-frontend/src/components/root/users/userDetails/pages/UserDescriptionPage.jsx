import React from "react";
import styles from "../../../../../css/UserDetails.module.css";

const UserDescriptionPage = ({ description, interests }) => {
    return (
        <>
            <div className={styles.user_description_page_main}>
                {description}
            </div>
            <br></br>
            <div className={styles.user_description_page_interests}>
                My interests are: {interests.map((interest) => (
                    <div key={interest} className={styles.interest_item}>
                        <span >{interest}</span>
                    </div>
                ))}
            </div>
            <br></br>
        </>
    );
};

export default UserDescriptionPage;