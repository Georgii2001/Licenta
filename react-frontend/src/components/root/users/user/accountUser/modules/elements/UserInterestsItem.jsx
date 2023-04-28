import React from "react";
import styles from "../../../../../../../css/Account.module.css";
import "react-confirm-alert/src/react-confirm-alert.css";
import { useState } from "react";

const UserInterestsItem = ({ saveUserInterests, removeUserInterests, interest }) => {

    const [isPressed, setIsPressed] = useState(false);
    const onPressed = (interest) => {
        setIsPressed(!isPressed);
        if (isPressed) {
            removeUserInterests(interest);
            
        } else {
            saveUserInterests(interest);

        }
    }

    return (
        <>
            <div className={isPressed ? styles.popup_item_clicked : styles.popup_item} onClick={() => onPressed(interest)}>
                <span className={styles.popup_item_container}>{interest}</span>
                <span className={isPressed ? styles.checkmark_clicked : styles.checkmark}>
                    <div className={isPressed ? styles.checkmark_stem_clicked : styles.checkmark_stem}></div>
                    <div className={isPressed ? styles.checkmark_kick_clicked : styles.checkmark_kick}></div>
                </span>
            </div>
        </>
    );
};

export default UserInterestsItem;
