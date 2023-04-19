import axios from "../customAxiosConfig/CustomAxiosConfig";
import AuthenticationService from "../authentication/AuthenticationService";
import qs from 'qs';

const UpdateUserInterestsService = (ids) => {
    let username = AuthenticationService.getLoggedInUser();
    const interestsListIds = ids.join(",");

    try {
        return axios.post(`/saveUserInterests`, null, {
            params: {
                interestsListIds
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

export default UpdateUserInterestsService;
