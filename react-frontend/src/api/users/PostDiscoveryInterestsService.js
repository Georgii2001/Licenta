import axios from "../customAxiosConfig/CustomAxiosConfig";

const PostDiscoveryInterestsService = (userId, interestNames) => {
    const interestsList = interestNames.join(",");
    
    try {
        return axios.post(`/discoverInterests`, null, {
            params: {
                userId,
                interestsList
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

export default PostDiscoveryInterestsService;
