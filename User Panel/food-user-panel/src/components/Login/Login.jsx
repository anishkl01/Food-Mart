import React, { useContext, useState } from "react";
import "./Login.css";
import { Link, useNavigate } from "react-router-dom";
import { loginUser } from "../../service/AuthService";
import { StoreContext } from "../../context/StoreContext";
import { toast } from "react-toastify";

const Login = () => {
  const navigate = useNavigate();

  const { setToken, loadCartData } = useContext(StoreContext);

  const [data, setData] = useState({
    email: "",
    password: "",
  });

  const onChangeHanlder = (e) => {
    const name = e.target.name;
    const value = e.target.value;

    setData({ ...data, [name]: value });
  };

  const onSubmitHandler = async (e) => {
    e.preventDefault();
    try {
      const response = await loginUser(data);
      console.log(response.status);
      if (response.status == 200) {
        setToken(response.data.token);
        localStorage.setItem("token", response.data.token);
        await loadCartData(response.data.token);
        navigate("/home");
      } else {
        toast.error("Unable to login. Please try again1.");
      }
    } catch (error) {
      toast.error("Unable to login. Please try again.");
    }
  };

  return (
    <div className="container mt-5">
      <div className="row justify-content-center">
        <div className="col-md-4">
          <div className="card p-4 shadow-sm">
            <h2 className="text-center mb-4">Login</h2>
            <form onSubmit={onSubmitHandler}>
              <div className="mb-3">
                <label className="form-label">Email address</label>
                <input
                  type="email"
                  className="form-control"
                  placeholder="Enter email"
                  name="email"
                  value={data.email}
                  onChange={onChangeHanlder}
                />
              </div>

              <div className="mb-3">
                <label className="form-label">Password</label>
                <input
                  type="password"
                  className="form-control"
                  placeholder="Enter password"
                  name="password"
                  value={data.password}
                  onChange={onChangeHanlder}
                />
              </div>

              <button type="submit" className="btn btn-primary w-100 mb-3">
                Login
              </button>
              <button type="reset" className="btn btn-danger w-100">
                Reset
              </button>
              <div className="text-center mt-4">
                Don't have an account? <Link to={"/register"}>Sign Up</Link>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Login;
