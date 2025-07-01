import React, { useState } from "react";
import { Route, Routes } from "react-router-dom";
import Sidebar from "./components/Sidebar/Sidebar";
import Menubar from "./components/Menubar/Menubar";
import AddFood from "./pages/AddFood/AddFood";
import ListFood from "./pages/ListFood/ListFood";
import Orders from "./pages/Orders/Orders";
import { ToastContainer, toast } from "react-toastify";

const App = () => {
  const [sidebarVisible, setSidebarVisible] = useState(true);

  const toggleSideBar = () => {
    setSidebarVisible(!sidebarVisible);
  };

  return (
    <div className="d-flex" id="wrapper">
      <Sidebar sidebar={sidebarVisible} />
      <div id="page-content-wrapper">
        <Menubar togglesidebar={toggleSideBar} />
        <ToastContainer />
        <div className="container-fluid">
          <Routes>
            <Route path="/add" element={<AddFood />}></Route>
            <Route path="/list" element={<ListFood />}></Route>
            <Route path="/orders" element={<Orders />}></Route>
            <Route path="/" element={<ListFood />}></Route>
          </Routes>
        </div>
      </div>
    </div>
  );
};

export default App;
