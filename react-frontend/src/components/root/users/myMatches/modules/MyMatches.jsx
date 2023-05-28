import React from "react";
import styles from "../../../../../css/MyMatches.module.css";
import { useNavigate, Link } from "react-router-dom";
import Tooltip from '@mui/material/Tooltip';
import { useMediaQuery } from "beautiful-react-hooks";
import AuthenticationService from "../../../../../api/authentication/AuthenticationService";

const MyMatches = ({ avatarFile, username, id }) => {

    const navigate = useNavigate();
    const isColumnBasedSmall = useMediaQuery("(max-width: 385px)");
    const isUserLoggedIn = AuthenticationService.isUserLoggedIn();

    const handleDetails = (value) => (event) => {
        event.preventDefault();

        if (isUserLoggedIn) {
            navigate(`/matched-user-details`, {
                state: { id: value },
            });
        }
    };

    return (
        <>
            <div className={styles.my_matches_item}>
                <div className={styles.image_container} onClick={handleDetails(id)} style={{ backgroundImage: "url(data:image/png;base64," + avatarFile + ")" }}>
                </div>
                <div className={styles.username_block}>
                    <div className={styles.username_redirect} onClick={handleDetails(id)}>
                        <Tooltip title={<h3>Tap to see more details</h3>}>
                            <div>{username}</div>
                        </Tooltip>
                    </div>
                </div>
                <div className={styles.send_email_button_container}>
                    <Link to="https://www.google.com/gmail/about/">
                        <div className={styles.send_email_button}>
                            {isColumnBasedSmall ? "Mail" : "Send email"}
                        </div>
                    </Link>
                </div>
            </div>
        </>
    )
}

export default MyMatches;