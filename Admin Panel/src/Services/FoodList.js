import axios from "axios";

const API_URL = "http://localhost:8080/api/foods";

export const foodList = async () => {
  try {
    const response = await axios.get(API_URL);
    return response;
  } catch (error) {
    throw error;
  }
};
