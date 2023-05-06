import axios from "../customAxiosConfig/CustomAxiosConfig";
import AuthenticationService from "../authentication/AuthenticationService";

const UpdateUserMainAvatar = (avatarId) => {
    let username = AuthenticationService.getLoggedInUser();

    try {
        return axios.post(`/changeMainAvatar`, null, {
            params: {
                username,
                avatarId
            },
        });
    } catch (err) {
        let error = "";
        if (err.response) {
            error += err.response;
        }
        return error;
    }
};

export default UpdateUserMainAvatar;
