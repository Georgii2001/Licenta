import React from "react";
import "react-confirm-alert/src/react-confirm-alert.css";
import { useLayoutEffect } from "react";
import styles from "../../../../../../css/Account.module.css";
import ImageList from '@mui/material/ImageList';
import ImageListItem from '@mui/material/ImageListItem';
import Tooltip from '@mui/material/Tooltip';
import PostUserAvatarService from "../../../../../../api/users/PostUserAvatarService";
import DeleteUserAvatarService from "../../../../../../api/users/DeleteUserAvatarService";
import UpdateuserMainAvatar from "../../../../../../api/users/UpdateUserMainAvatar";

const UserMedia = ({ userAvatars, refreshUserData }) => {

    const avatarInput = React.useRef(null);

    const handleClick = event => {
        avatarInput.current.click();
    };

    const changeMainAvatar = async (avatarName) => {
        await UpdateuserMainAvatar(avatarName);

        // GetUserAvatarService().then((response) => {
        //     setUserAvatars(response.data);
        // });
    };

    const saveAvatarInDB = async (avatar) => {
        const formData = new FormData();
        formData.append("avatar", avatar);
        formData.append("avatarName", avatar.name)
        await PostUserAvatarService(formData);

        // GetUserAvatarService().then((response) => {
        //     setUserAvatars(response.data);
        // });
    };

    const removeAvatarFromDB = async (avatarName) => {
        await DeleteUserAvatarService(avatarName);

        // GetUserAvatarService().then((response) => {
        //     setUserAvatars(response.data);
        // });
    };

    useLayoutEffect(() => {
        // GetUserAvatarService().then((response) => {
        //     setUserAvatars(response.data);
        // });
    }, []);

    return (
        <>
            <div className={styles.account_content}>
                <span className={styles.account_title}>
                    <b>Media</b>
                </span>
                <hr className={styles.account_hr}></hr>
                <div className={styles.upload_wrapper}>
                    {userAvatars && userAvatars.length ?
                        <ImageList cols={3} gap={8} sx={{ overflowY: 'hidden' }}>
                            {userAvatars.map((avatar, index) => (
                                <ImageListItem key={avatar.avatarId} className={styles.avatar_style} >
                                    <img
                                        title={avatar.avatarId}
                                        src={`data:image/png;base64,${avatar.avatarFile}`}
                                        srcSet={`data:image/png;base64,${avatar.avatarFile}`}
                                        loading="lazy"
                                    />
                                    {index === 0 ? null :
                                        <div>
                                            <Tooltip title={<h3>Set as profile picture</h3>}>
                                                <button className={styles.set_profile_photo_button} onClick={() => { changeMainAvatar(avatar.avatarId) }}></button>
                                            </Tooltip>
                                            <Tooltip title={<h3>Remove photo</h3>}>
                                                <div className={styles.remove_photo_button} onClick={() => { removeAvatarFromDB(avatar.avatarId) }}></div>
                                            </Tooltip>
                                        </div>
                                    }
                                </ImageListItem>
                            ))}
                            <span>
                                <div className={styles.add_more_photo} onClick={handleClick}>
                                    <span>Add more photo</span>
                                </div>
                                <input
                                    id="avatar"
                                    name="avatar"
                                    type="file"
                                    ref={avatarInput}
                                    style={{ display: "none" }}
                                    onChange={(e) => {
                                        const file = e.target.files[0];
                                        saveAvatarInDB(file);
                                    }} />
                            </span>
                        </ImageList>
                        :
                        <ImageList variant="masonry" cols={1} gap={8}>
                            <span>
                                <div className={styles.add_more_photo} onClick={handleClick}>
                                    <span>Add more photo</span>
                                </div>
                                <input
                                    id="avatar"
                                    name="avatar"
                                    type="file"
                                    ref={avatarInput}
                                    styles={{ display: "none" }}
                                    onChange={(e) => {
                                        const file = e.target.files[0];
                                        const reader = new FileReader();
                                        saveAvatarInDB(file);
                                    }} />
                            </span>
                        </ImageList>
                    }
                </div>
                <br></br>
            </div >

        </>
    );
};

export default UserMedia;
