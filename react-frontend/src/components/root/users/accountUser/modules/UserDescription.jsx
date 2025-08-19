import { React, useState, useRef } from "react";
import styles from "../../../../../css/Account.module.css";
import editButtonImg from "../../../../../img/editButton.png";
import UpdateUserDataService from "../../../../../api/users/UpdateUserDataService";

const UserDescription = ({ email, description, refreshUserData }) => {

    const textAreaRef = useRef(null);
    const [userInfo, setUserInfo] = useState({
        email: "",
        description: ""
    });

    const editUserDescription = () => {
        textAreaRef.current.disabled = false;
        textAreaRef.current.focus();
        const textLength = textAreaRef.current.defaultValue.length;
        textAreaRef.current.setSelectionRange(textLength, textLength);
    }

    const changeUserDescription = (event) => {
        setUserInfo({ ...userInfo, email: email, description:event.target.value});
    }

    const saveUserDescription = async (event) => {
        textAreaRef.current.disabled = true;

        await UpdateUserDataService(userInfo);

        refreshUserData();
    }

    return (
        <>
            <div className={styles.account_content}>
                <span className={styles.account_title}>
                    <b>Description</b>
                </span>
                <hr className={styles.account_hr}></hr>
                <textarea ref={textAreaRef} disabled={true} onChange={changeUserDescription} onBlur={saveUserDescription} className={styles.textarea_style} placeholder="Tell something about you..." defaultValue={description} rows={4}></textarea>
                <button className={styles.edit_description_button} onClick={editUserDescription}>
                    <div className={styles.edit_button_img} style={{ backgroundImage: "url(" + editButtonImg + ")" }}></div>
                    Edit description
                </button>
                <br></br>
            </div >
        </>
    );
};

export default UserDescription;