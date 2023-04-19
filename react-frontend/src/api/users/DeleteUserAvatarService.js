import axios from "../customAxiosConfig/CustomAxiosConfig";

const DeleteUserAvatarService = (userAvatarName) => {
    try {
        const res = axios.delete(`/deleteUserAvatar`, {
            params: {
                userAvatarName,
            },
        });

        return res;
    } catch (err) {
        let error = "";
        if (err.response) {
            error += err.response;
        }
        return error;
    }
};

export default DeleteUserAvatarService;
