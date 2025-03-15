import React, { useState, useEffect, forwardRef } from "react";
import { assets } from "../../assets/assets";
import { addFood } from "../../Services/FoodService";
import { toast } from "react-toastify";

const AddFood = () => {
  const [image, setImage] = useState(false);
  const [data, setData] = useState({
    name: "",
    description: "",
    category: "Choose",
    price: "",
  });

  const onChangeHandler = (e) => {
    const name = e.target.name;
    const value = e.target.value;
    setData((data) => ({ ...data, [name]: value }));
  };

  const onSubmitHandler = async (e) => {
    e.preventDefault();
    if (!image) {
      toast.error("Please add the image");
      return;
    }
    try {
      await addFood(data, image);
      toast.success("Food added successfully.");
      setData({ name: "", description: "", category: "Choose", price: "" });
      setImage(null);
    } catch (error) {
      toast.error("Error adding food");
    }
  };

  return (
    <div className="mx-4 mt-2">
      <div className="row">
        <div className="card col-md-4">
          <form className="card-body" onSubmit={onSubmitHandler}>
            <h2 className="text-center mb-4">Add Food</h2>
            <div className="mb-3">
              <label htmlFor="file" className="form-label">
                <img
                  src={image ? URL.createObjectURL(image) : assets.upload}
                  alt=""
                  width={98}
                />
              </label>
              <input
                type="file"
                className="form-control"
                id="file"
                onChange={(e) => setImage(e.target.files[0])}
                hidden
              />
            </div>
            <div className="mb-3">
              <label htmlFor="name" className="form-label">
                Name
              </label>
              <input
                type="text"
                className="form-control"
                id="name"
                name="name"
                placeholder="Chicken Biryani"
                required
                onChange={onChangeHandler}
                value={data.name}
              />
            </div>
            <div className="mb-3">
              <label htmlFor="category" className="form-label">
                Category
              </label>
              <select
                name="category"
                id="category"
                className="form-control"
                onChange={onChangeHandler}
                value={data.category}
              >
                <option value="">Choose</option>
                <option value="Biryani">Biryani</option>
                <option value="Pizza">Pizza</option>
                <option value="Cake">Cake</option>
                <option value="Burger">Burger</option>
              </select>
            </div>
            <div className="mb-3">
              <label htmlFor="price" className="form-label">
                Price
              </label>
              <input
                type="number"
                id="price"
                name="price"
                placeholder="200"
                className="form-control"
                onChange={onChangeHandler}
                value={data.price}
              />
            </div>
            <div className="mb-3">
              <label htmlFor="description" className="form-label">
                Description
              </label>
              <textarea
                className="form-control"
                id="description"
                name="description"
                placeholder="Write content here..."
                rows="5"
                required
                onChange={onChangeHandler}
                value={data.description}
              ></textarea>
            </div>
            <div className="d-grid">
              <button type="submit" className="btn btn-primary btn-lg">
                Add Food
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
};

export default AddFood;
