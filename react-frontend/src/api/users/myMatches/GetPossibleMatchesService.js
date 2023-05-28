import AuthenticationService from "../../authentication/AuthenticationService";
import axios from "../../customAxiosConfig/CustomAxiosConfig";

const GetPossibleMatchesService = () => {
  let username = AuthenticationService.getLoggedInUser();

  try {

    return axios.get(`/possible-matches`, {
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

export default GetPossibleMatchesService;



