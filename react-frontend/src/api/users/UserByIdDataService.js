import axios from "../customAxiosConfig/CustomAxiosConfig";

const UserByIdDataService = (id) => {
  try {
    return axios.get(`/client`, {
      params: {
        id,
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

export default UserByIdDataService;
