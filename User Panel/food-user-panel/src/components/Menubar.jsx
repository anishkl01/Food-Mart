import React, { useContext, useState } from "react";
import "./Menubar.css";
import { assets } from "../assets/assets";
import { Link, useNavigate } from "react-router-dom";
import { StoreContext } from "../context/StoreContext";

const Menubar = () => {
  const { quantities, token, setToken, setQuantities } =
    useContext(StoreContext);
  const [active, setActive] = useState("home");

  const navigate = useNavigate();

  const uniqueItemsCart = Object.values(quantities).filter(
    (qty) => qty > 0
  ).length;

  const handleLogout = () => {
    localStorage.removeItem("token");
    setToken("");
    setQuantities({});
    navigate("/home");
  };

  return (
    <nav className="navbar navbar-expand-lg bg-body-tertiary">
      <div className="container">
        <Link to={"/home"}>
          <img src={assets.logo} className="mx-4" height={48} width={48} />
        </Link>
        <button
          className="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#navbarSupportedContent"
          aria-controls="navbarSupportedContent"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <span className="navbar-toggler-icon"></span>
        </button>
        <div className="collapse navbar-collapse" id="navbarSupportedContent">
          <ul className="navbar-nav me-auto mb-2 mb-lg-0">
            <li className="nav-item">
              <Link
                className={active === "home" ? "nav-link active" : "nav-link"}
                aria-current="page"
                to={"/home"}
                onClick={() => setActive("home")}
              >
                Home
              </Link>
            </li>
            <li className="nav-item">
              <Link
                className={
                  active === "explore" ? "nav-link active" : "nav-link"
                }
                to={"/explore"}
                onClick={() => setActive("explore")}
              >
                Explore
              </Link>
            </li>
            <li className="nav-item">
              <Link
                className={
                  active === "contact-us" ? "nav-link active" : "nav-link"
                }
                to={"/contact"}
                onClick={() => setActive("contact-us")}
              >
                Contact Us
              </Link>
            </li>
          </ul>
          <div className="d-flex align-items gap-4">
            <Link to={"/cart"}>
              <div className="position-relative">
                <img
                  src={assets.trolley}
                  alt=""
                  height={32}
                  width={32}
                  className="position-relative"
                />
                <span className="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-warning">
                  {uniqueItemsCart}
                </span>
              </div>
            </Link>
            {!token ? (
              <>
                <button
                  className="btn btn-outline-primary"
                  onClick={() => navigate("/login")}
                >
                  Login
                </button>
                <button
                  className="btn btn-outline-success"
                  onClick={() => navigate("/register")}
                >
                  Register
                </button>
              </>
            ) : (
              <div className="dropdown text-end">
                <button
                  className="btn dropdown-toggle"
                  type="button"
                  id="dropdownMenuButton"
                  data-bs-toggle="dropdown"
                  aria-expanded="false"
                >
                  <img
                    src="https://cdn-icons-png.flaticon.com/512/3135/3135715.png"
                    alt="profile"
                    width="40"
                    height="40"
                    className="rounded-circle"
                  />
                </button>
                <ul
                  className="dropdown-menu dropdown-menu-end"
                  aria-labelledby="dropdownMenuButton"
                >
                  <li>
                    <button
                      className="dropdown-item"
                      onClick={() => navigate("/my-orders")}
                    >
                      My Orders
                    </button>
                  </li>
                  <li>
                    <button className="dropdown-item" onClick={handleLogout}>
                      Logout
                    </button>
                  </li>
                </ul>
              </div>
            )}
          </div>
        </div>
      </div>
    </nav>
  );
};

export default Menubar;
