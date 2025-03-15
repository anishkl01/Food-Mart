import axios from "axios";

const API_URL = "http://localhost:8080/api/foods";

export const addFood = async (fooddata, image) => {
  const formdata = new FormData();
  formdata.append("food", JSON.stringify(fooddata));
  formdata.append("file", image);
  try {
    const response = await axios.post(API_URL, formdata, {
      headers: { "Content-Type": "multipart/form-data" },
    });
  } catch (error) {
    throw error;
  }
};
