import useFetch from "../../hooks/useFetch";
import Billing from "../../services/Billing";
import React, {useEffect} from "react";

const Order = ({location}) => {


    const headers = {
        // email: localStorage.get("email"),
        // session_id: localStorage.get("session_id"),
        email: "apiGatewayTest@uci.edu",
        session_id: "d0235ee6d5701c537ff0803c1ae68252dcf0e0cd59ffa19391609d6802b4403470800e932e0caf59082ad4b9ca6840a080f3efee0103902ebe8c9b2b74a75bad",
        // transaction_id: "dsafasdgasdg"
    }



    const orders = useFetch("apiGatewayTest@uci.edu", headers, Billing.orderRetrieve)?.transactions;
    // "transactions": [
    //     {
    //         "capture_id": “9TD06540UA4976920”,
    // "state": "completed",
    //     "amount":
    // {
    //     "total": "162.04",
    //     "currency": "USD",
    // },
    // "transaction_fee":
    // {
    //     "value": "5.81",
    //     "currency": "USD"
    // },
    // "create_time": "2019-05-07T20:08:39Z",
    //     "update_time": "2019-05-07T20:08:39Z",


    return (
        <div>
            <h1 className="text-center">My Orders</h1>
            {orders?
                orders?.map((order => {

                    return (
                        <div className="card mb-3 " key={order.capture_id}>
                            <div className="card-body">
                                <h5 className="card-title">Order ID:{order.capture_id}</h5>
                                <p className="card-text">Price: {order.amount.total}</p>
                                <p className="card-text">Date: {order.create_time}</p>

                            </div>
                        </div>)
                }))
                :<h2>No order found</h2>
            }

        </div>
    )
}
export default Order;