import React, { useEffect, useState } from "react";
import FoodDisplay from "../../components/FoodDisplay/FoodDisplay";

const ExplodeFood = () => {
  const [category, setCategory] = useState("All");
  const [searchText, setSearchText] = useState("");

  return (
    <>
      <div className="container">
        <div className="row justify-content-center">
          <div className="col-md-6">
            <form onSubmit={(e) => e.preventDefault()}>
              <div className="input-group mb-3">
                <select
                  name=""
                  id=""
                  className="form-select mt-2"
                  style={{ maxWidth: "150px" }}
                  onChange={(e) => setCategory(e.target.value)}
                >
                  <option value="All">All</option>
                  <option value="Biryani">Biryani</option>
                  <option value="Burger">Burger</option>
                  <option value="Cakes">Cakes</option>
                  <option value="Ice Creams">Ice Creams</option>
                  <option value="Pizza">Pizza</option>
                  <option value="Salad">Salad</option>
                </select>
                <input
                  type="text"
                  className="form-control mt-2"
                  placeholder="Search your favourite dish"
                  onChange={(e) => setSearchText(e.target.value)}
                  value={searchText}
                />
                <button className="btn btn-primary mt-2">
                  <i className="bi bi-search"></i>
                </button>
              </div>
            </form>
          </div>
        </div>
      </div>
      <FoodDisplay category={category} searchText={searchText} />
    </>
  );
};

export default ExplodeFood;
