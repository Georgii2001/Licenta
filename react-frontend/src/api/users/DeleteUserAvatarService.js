import axios from "../customAxiosConfig/CustomAxiosConfig";
import AuthenticationService from "../authentication/AuthenticationService";

const DeleteUserAvatarService = (avatarId) => {
    let username = AuthenticationService.getLoggedInUser();

    try {
        const res = axios.delete(`/avatar`, {
            params: {
                username,
                avatarId,
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
