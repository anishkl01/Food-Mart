import React, { useEffect, useState } from "react";
import { foodList } from "../../Services/FoodList";
import axios from "axios";
import { toast } from "react-toastify";
import { deleteRequest } from "../../Services/DeleteFood";

const ListFood = () => {
  const [foods, setFoodList] = useState([]);

  const foodFetch = async () => {
    const response = await foodList();
    console.log(response.data);
    if (response.status == 200) {
      setFoodList(response.data);
    } else {
      toast.error("Something Went Wrong While Fetching Foods..");
    }
  };

  const deleteFood = async (foodId) => {
    const response = await deleteRequest(foodId);
    if (response.status === 204) {
      toast.success("Food deleted successfully");
      await foodFetch();
    } else {
      toast.error("Something went wrong");
    }
  };

  useEffect(() => {
    foodFetch();
  }, []);

  return (
    <div className="container mt-4">
      <h2 className="text-center mb-4">Food List</h2>
      <div className="table-responsive">
        <table className="table table-striped table-bordered">
          <thead className="table-dark">
            <tr>
              <th>#</th>
              <th>Image</th>
              <th>Name</th>
              <th>Category</th>
              <th>Description</th>
              <th>Price (₹)</th>
              <th>Action</th>
            </tr>
          </thead>
          <tbody>
            {foods.length > 0 ? (
              foods.map((food, index) => (
                <tr key={food.id}>
                  <td>{index + 1}</td>
                  <td>
                    <img
                      src={food.imageUrl}
                      alt={food.name}
                      style={{
                        width: "50px",
                        height: "50px",
                        objectFit: "cover",
                      }}
                      className="rounded"
                    />
                  </td>
                  <td>{food.name}</td>
                  <td>{food.category}</td>
                  <td>{food.description}</td>
                  <td className="fw-bold text-success">₹{food.price}</td>
                  <td>
                    <button
                      className="btn btn-danger btn-sm"
                      onClick={() => deleteFood(food.id)}
                    >
                      <i className="bi bi-trash-fill"></i>
                    </button>
                  </td>
                </tr>
              ))
            ) : (
              <tr>
                <td colSpan="7" className="text-center text-muted">
                  No records found
                </td>
              </tr>
            )}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default ListFood;
