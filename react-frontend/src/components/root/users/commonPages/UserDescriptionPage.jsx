import React from "react";
import styles from "../../../../css/UserDetails.module.css";

const UserDescriptionPage = ({ description, interests }) => {
    return (
        <>
            {description ?
                <div className={styles.user_description_page_main}>
                    {description}
                </div> :
                <div className={styles.user_description_page_interests}>
                    No description at this moment :(
                </div>
            }
            <br></br>
            {interests && interests.length ?
                <div className={styles.user_description_page_interests}>
                    My interests are: {interests.map((interest) => (
                        <div key={interest} className={styles.interest_item}>
                            <span >{interest}</span>
                        </div>
                    ))}
                </div>
                :
                <div className={styles.user_description_page_interests}>
                    No interests at this moment :(
                </div>
            }
            <br></br>
        </>
    );
};

export default UserDescriptionPage;