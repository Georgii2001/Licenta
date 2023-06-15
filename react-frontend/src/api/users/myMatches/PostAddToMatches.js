import AuthenticationService from "../../authentication/AuthenticationService";
import axios from "../../customAxiosConfig/CustomAxiosConfig";

const PostAddToMatches = (matchedUserId, status) => {
    let username = AuthenticationService.getLoggedInUser();

    try {
        return axios.post(`/add-to-matches`, null, {
            params: {
                username,
                matchedUserId,
                status
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

export default PostAddToMatches;



