import axios from "../customAxiosConfig/CustomAxiosConfig";

const RemoveUserInterestService = (id) => {
  try {
    return axios.delete(`/removeInterests`, {
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

export default RemoveUserInterestService;
