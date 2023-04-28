import axios from "../customAxiosConfig/CustomAxiosConfig";
import AuthenticationService from "../authentication/AuthenticationService";

const DeleteUserInterestService = (interest) => {
  let username = AuthenticationService.getLoggedInUser();

  try {
    return axios.delete(`/userInterests`, {
      params: {
        interest,
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

export default DeleteUserInterestService;
