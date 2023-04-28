import React from "react";
import { useNavigate } from "react-router-dom";
import styles from "../../../../../../css/Account.module.css";
import "react-confirm-alert/src/react-confirm-alert.css";
import { Link } from "react-router-dom";
import DeleteUserService from "../../../../../../api/users/DeleteUserService";
import { confirmAlert } from "react-confirm-alert";
import AuthenticationService from "../../../../../../api/authentication/AuthenticationService";

const UserInfo = ({ user }) => {
    const navigate = useNavigate();

    const handleDelete = (user) => (event) => {
        event.preventDefault();
        confirmAlert({
            title: "Delete Profile",
            message: "Are you sure you want to delete your profile?",
            buttons: [
                {
                    label: "Yes",
                    onClick: async () => {
                        const response = await DeleteUserService(user.id);
                        if (response.data !== null) {
                            AuthenticationService.logout();
                        }
                    },
                },
                {
                    label: "No",
                },
            ],
        });
    };

    const handleEdit = (user) => (event) => {
        event.preventDefault();
        let path = "/edit-profile";
        navigate(path, {
            state: { id: user.id, gender: user.gender },
        });
    };

    return (
        <>
            <div className={styles.account_content}>
                <span className={styles.account_title}>
                    <b>Account info</b>
                </span>
                <hr className={styles.account_hr}></hr>
                <br></br>
                <p> Username: {user.username} </p>
                <p> Email: {user.email}</p>
                <p> Gender: {user.gender} </p>
                <p> Change password: **** </p>
                <br></br>
                <article className={styles.account_buttons}>
                    <Link
                        to="#"
                        onClick={handleDelete(user)}
                        className={styles.account_btn}
                    >
                        Delete
                    </Link>
                    <Link
                        to="#"
                        onClick={handleEdit(user)}
                        className={styles.account_btn}
                    >
                        Edit
                    </Link>
                </article>
            </div>
        </>
    );
};

export default UserInfo;
