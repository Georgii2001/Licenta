import AuthenticationService from "../../authentication/AuthenticationService";
import axios from "../../customAxiosConfig/CustomAxiosConfig";

const PostSendNotification = (receiverId) => {
    let username = AuthenticationService.getLoggedInUser();

    try {
        return axios.post(`/send-invitation`, null, {
            params: {
                username,
                receiverId
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

export default PostSendNotification;



