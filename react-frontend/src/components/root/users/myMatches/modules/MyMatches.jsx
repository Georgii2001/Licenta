import React from "react";
import styles from "../../../../../css/MyMatches.module.css";
import { useNavigate, Link } from "react-router-dom";
import Tooltip from '@mui/material/Tooltip';
import { useMediaQuery } from "beautiful-react-hooks";
import AuthenticationService from "../../../../../api/authentication/AuthenticationService";
import PostSendNotification from "../../../../../api/users/myMatches/PostSendNotification";
import {NotificationContainer, NotificationManager} from 'react-notifications';

import 'react-notifications/lib/notifications.css';

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

    const sendEmail = (id) => {
        PostSendNotification(id).then((response) => {
            if(response.data.messageStatus === "newMessageSent") {
                NotificationManager.success("Message was succesfully sent!", "", 1000);
            } else {
                NotificationManager.warning("Message was already sent!", "", 1000); 
            }
        });
    }

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
                <div className={styles.send_email_button_container} onClick={() => sendEmail(id)}>
                    <div className={styles.send_email_button} >
                        {isColumnBasedSmall ? "Mail" : "Send email"}
                    </div>
                </div>
            </div>
            <NotificationContainer />
        </>
    )
}

export default MyMatches;