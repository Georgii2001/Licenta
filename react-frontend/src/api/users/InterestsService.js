import axios from "../customAxiosConfig/CustomAxiosConfig";
import AuthenticationService from "../authentication/AuthenticationService";

const InterestsService = () => {
  let username = AuthenticationService.getLoggedInUser();
  
  try {
    return axios.get(`/interests`, {
      params: {
        username,
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

export default InterestsService; 