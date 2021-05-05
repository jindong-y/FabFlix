import React,{useEffect} from "react";
import Billing from "../../services/Billing";

function OrderComplete({location}) {
    const headers = {
        // email: localStorage.get("email"),
        // session_id: localStorage.get("session_id"),
        email: "apiGatewayTest@uci.edu",
        session_id: "d0235ee6d5701c537ff0803c1ae68252dcf0e0cd59ffa19391609d6802b4403470800e932e0caf59082ad4b9ca6840a080f3efee0103902ebe8c9b2b74a75bad",
        // transaction_id: "dsafasdgasdg"
    }
    console.log(location)
    console.log(new URLSearchParams(location.search).get("token"))


    const [result, setResult]=React.useState();

    useEffect(async () => {
        const response = await Billing.orderComplete(location.search, headers)
        console.log(response)
        if (response?.data.resultCode === 3420) {
            setResult("Order is completed successfully.")
        }else{
            setResult("Error, Order is not completed.")
        }
        console.log(result)
    }, [])

    return <h1>{result}</h1>
}
export default OrderComplete;