import AuthenticationService from "../../authentication/AuthenticationService";
import axios from "../../customAxiosConfig/CustomAxiosConfig";

const GetMyMatchesService = () => {
  let username = AuthenticationService.getLoggedInUser();

  try {

    return axios.get(`/get-my-matches`, {
      params: {
        username
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

export default GetMyMatchesService;



