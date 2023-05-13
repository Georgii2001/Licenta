import {React, useLayoutEffect} from "react";
import styles from "../../../../../css/UserDetails.module.css";
import { ImageList, ImageListItem, Tooltip } from "@mui/material";

const UserMediaPage = ({ avatars }) => {
      
    return (
        <>
        {avatars && avatars.length ?
            <ImageList cols={3} gap={8} sx={{ overflowY: 'hidden' }}>
                {avatars.map((avatar) => (
                    <ImageListItem key={avatar.avatarId} >
                        <img
                            title={avatar.avatarId}
                            src={`data:image/png;base64,${avatar.avatarFile}`}
                            srcSet={`data:image/png;base64,${avatar.avatarFile}`}
                            loading="lazy"
                        />
                    </ImageListItem>
                ))}
            </ImageList>
            :
            null
            }

            {/* <div className={styles.user_description_page_main}>
                {description}
            </div>
            <br></br>
            <div className={styles.user_description_page_interests}>
                My interests are: {interests.map((interest) => (
                    <div key={interest} className={styles.interest_item}>
                        <span >{interest}</span>
                    </div>
                ))}
            </div> */}
            <br></br>
        </>
    );
};

export default UserMediaPage;