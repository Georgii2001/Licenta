import { React } from "react";
import { ImageList, ImageListItem } from "@mui/material";

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
            <br></br>
        </>
    );
};

export default UserMediaPage;