import axios from "../customAxiosConfig/CustomAxiosConfig";
import AuthenticationService from "../authentication/AuthenticationService";

const UpdateuserMainAvatar = (userAvatarName) => {
    let username = AuthenticationService.getLoggedInUser();

    try {
        return axios.post(`/changeUserMainAvatar`, null, {
            params: {
                username,
                userAvatarName
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

export default UpdateuserMainAvatar;
