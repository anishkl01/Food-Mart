import React, { useRef, useState } from "react";
import { categories } from "../../assets/assets";
import "./ExploreMenu.css";

const ExploreMenu = ({ category, setCategory }) => {
  const exploreMenueRef = useRef(null);

  const scrollRight = () => {
    if (exploreMenueRef.current) {
      exploreMenueRef.current.scrollBy({ left: 200, behavior: "smooth" });
    }
  };

  const scrollLeft = () => {
    if (exploreMenueRef.current) {
      exploreMenueRef.current.scrollBy({ left: -200, behavior: "smooth" });
    }
  };

  return (
    <div className="explore-menu position-relative">
      <h1 className="d-flex align-items-center justify-content-between">
        Explore Our Menu
      </h1>
      <div className="d-flex">
        <i
          className="bi bi-arrow-left-circle scroll-icon"
          onClick={scrollLeft}
        ></i>
        <i
          className="bi bi-arrow-right-circle scroll-icon"
          onClick={scrollRight}
        ></i>
      </div>
      <p>Explore curated lists of dishes from top categories</p>
      <div
        className="d-flex justify-content-between gap-4 overflow-auto explore-menu-list"
        ref={exploreMenueRef}
      >
        {categories.map((items, index) => {
          return (
            <div
              key={index}
              className="text-center explore-menu-list-item"
              onClick={() =>
                setCategory((prev) =>
                  prev === items.category ? "All" : items.category
                )
              }
            >
              <img
                src={items.icon}
                height={128}
                width={128}
                className={
                  items.category == category
                    ? "rounded-circle active"
                    : "rounded-cicle"
                }
              />
              <p className="mt-2 fw-bold">{items.category}</p>
            </div>
          );
        })}
      </div>
      <hr />
    </div>
  );
};

export default ExploreMenu;
