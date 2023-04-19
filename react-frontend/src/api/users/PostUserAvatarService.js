import axios from "../customAxiosConfig/CustomAxiosConfig";
import AuthenticationService from "../authentication/AuthenticationService";

const PostUserAvatarService = (avatar) => {
  let username = AuthenticationService.getLoggedInUser();

  try {
    return axios.post(`/userAvatar`, avatar, {
      headers: {
        "Content-Type": "multipart/form-data",
      },
      params: {
        username,
      }
    });
  } catch (err) {
    let error = "";
    if (err.response) {
      error += err.response;
    }
    return error;
  }
};

export default PostUserAvatarService;
