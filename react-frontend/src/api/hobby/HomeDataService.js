import AuthenticationService from "../authentication/AuthenticationService";
import axios from "../customAxiosConfig/CustomAxiosConfig";

const HomeDataService = (page) => {
  let username = AuthenticationService.getLoggedInUser();
  const isUserLoggedIn = AuthenticationService.isUserLoggedIn();

  try {
    let role = "business";

    if (isUserLoggedIn) {
      role = "user";
    }

    return axios.get(`/home`, {
      params: {
        username,
        role,
        page,
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

export default HomeDataService;



