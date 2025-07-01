import React from "react";
import Menubar from "./components/Menubar";
import { Route, Routes } from "react-router-dom";
import Home from "./pages/Home/Home";
import ContactUs from "./pages/ContactUs/ContactUs";
import ExplodeFood from "./pages/Explore Food/ExplodeFood";
import FoodDetails from "./pages/Food Details/FoodDetails";
import Cart from "./pages/Cart/Cart";
import Placeholder from "./pages/Placeholder/Placeholder";
import Login from "./components/Login/Login";
import Register from "./components/Register/Register";
import { ToastContainer, toast } from "react-toastify";
import MyOrders from "./pages/MyOrders/MyOrders";

const App = () => {
  return (
    <div>
      <Menubar />
      <ToastContainer />
      <Routes>
        <Route path="/home" element={<Home />}></Route>
        <Route path="/contact" element={<ContactUs />}></Route>
        <Route path="/explore" element={<ExplodeFood />}></Route>
        <Route path="/food/:id" element={<FoodDetails />}></Route>
        <Route path="/cart" element={<Cart />}></Route>
        <Route path="/order" element={<Placeholder />}></Route>
        <Route path="/login" element={<Login />}></Route>
        <Route path="/register" element={<Register />}></Route>
        <Route path="/my-orders" element={<MyOrders />}></Route>
      </Routes>
    </div>
  );
};

export default App;
