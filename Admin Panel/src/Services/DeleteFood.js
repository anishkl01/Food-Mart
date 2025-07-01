import axios from "axios";

const API_URL = "http://localhost:8080/api/foods/";

export const deleteRequest = async (foodId) => {
  try {
    const response = await axios.delete(API_URL + foodId);
    return response;
  } catch (error) {
    throw error;
  }
};
