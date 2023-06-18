import React from "react";
import "react-confirm-alert/src/react-confirm-alert.css";
import styles from "../../../../../css/Account.module.css";
import ImageList from '@mui/material/ImageList';
import ImageListItem from '@mui/material/ImageListItem';
import Tooltip from '@mui/material/Tooltip';
import PostUserAvatarService from "../../../../../api/users/PostUserAvatarService";
import DeleteUserAvatarService from "../../../../../api/users/DeleteUserAvatarService";
import UpdateUserMainAvatar from "../../../../../api/users/UpdateUserMainAvatar";
import { Carousel } from "react-responsive-carousel";
import "react-responsive-carousel/lib/styles/carousel.min.css";

const UserMedia = ({ userAvatars, refreshUserData }) => {

    const avatarInput = React.useRef(null);

    const handleClick = event => {
        avatarInput.current.click();
    };

    const changeMainAvatar = async (avatarId) => {
        await UpdateUserMainAvatar(avatarId);

        refreshUserData();
    };

    const saveAvatarInDB = async (avatar) => {
        const formData = new FormData();
        formData.append("avatar", avatar);
        await PostUserAvatarService(formData);

        avatarInput.current.value = "";
        refreshUserData();
    };

    const removeAvatarFromDB = async (avatarId) => {
        await DeleteUserAvatarService(avatarId);

        refreshUserData();
    };

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
                                <ImageListItem key={avatar.avatarId} >
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
                                    <div className={styles.add_more_photo_text}>Add more photo</div>
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
                                        saveAvatarInDB(file);
                                    }} />
                            </span>
                        </ImageList>
                    }
                </div>
                {/* <div>
                {userAvatars && userAvatars.length ?
                <Carousel useKeyboardArrows={true} width="75%">
                    {userAvatars.map((avatar, index) => (
                        <div>
                            <div className="slide">
                                <img alt="sample_file" src={`data:image/png;base64,${avatar.avatarFile}`} key={index} />
                            </div> */}
                            {/* {index === 0 ? null :
                                <div>
                                    <Tooltip title={<h3>Set as profile picture</h3>}>
                                        <button className={styles.set_profile_photo_button} onClick={() => { changeMainAvatar(avatar.avatarId) }}></button>
                                    </Tooltip>
                                    <Tooltip title={<h3>Remove photo</h3>}>
                                        <div className={styles.remove_photo_button} onClick={() => { removeAvatarFromDB(avatar.avatarId) }}></div>
                                    </Tooltip>
                                </div>
                            } */}
                        {/* </div>
                    ))}
                </Carousel>: null}</div> */}
                <br></br>
            </div >

        </>
    );
};

export default UserMedia;
