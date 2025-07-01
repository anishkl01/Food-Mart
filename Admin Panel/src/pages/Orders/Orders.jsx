import React, { useContext, useEffect, useState } from "react";
import axios from "axios";
import { assets } from "../../assets/assets";

const Orders = () => {
  const [data, setData] = useState([]);

  const fetchOrders = async () => {
    const response = await axios.get("http://localhost:8080/api/orders/all");
    setData(response.data);
  };

  const updateStatus = async (event, orderId) => {
    const response = await axios.patch(
      `http://localhost:8080/api/orders/status/${orderId}?status=${event.target.value}`
    );

    if (response.status === 200) {
      await fetchOrders();
    }
  };

  useEffect(() => {
    fetchOrders();
  }, []);

  return (
    <div className="container">
      <div className="py-5 row justify-content-center">
        <div className="col-11 card">
          <table className="table table-responsive">
            <tbody>
              {data.map((order, index) => {
                return (
                  <tr key={index}>
                    <td>
                      <img src={assets.logo} alt="" height={48} width={48} />
                    </td>
                    <td>
                      {order.items.map((item, index) => {
                        if (index === order.items.length - 1) {
                          return item.name + "X" + item.quantity;
                        } else {
                          return item.name + "X" + item.quantity + ", ";
                        }
                      })}
                    </td>
                    <td>&#8377;{order.amount.toFixed(2)}</td>
                    <td>Items: {order.items.length}</td>
                    <td className="fw-bold text-capitalize">
                      &#x25cf;{order.orderStatus}
                    </td>
                    <td>
                      <select
                        name=""
                        id=""
                        className="form-control"
                        onChange={(event) => updateStatus(event, order.id)}
                        value={order.orderStatus}
                      >
                        <option value="Food preparing">Food preparing</option>
                        <option value="Out for delivery">
                          Out for delivery
                        </option>
                        <option value="Delivered">Delivered</option>
                      </select>
                    </td>
                  </tr>
                );
              })}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
};

export default Orders;
