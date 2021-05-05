import './menu-bar.css'
import React ,{useEffect}from "react";
import useFetch from "../../hooks/useFetch";
import Billing from "../../services/Billing";


const Cart=()=>{

    const [quantity,setQuantity]=React.useState([]);
    // const [cart,setCart]=React.useState([]);
    const headers = {
        // email: localStorage.get("email"),
        // session_id: localStorage.get("session_id"),
        email: "apiGatewayTest@uci.edu",
        session_id: "d0235ee6d5701c537ff0803c1ae68252dcf0e0cd59ffa19391609d6802b4403470800e932e0caf59082ad4b9ca6840a080f3efee0103902ebe8c9b2b74a75bad",
        // transaction_id: "dsafasdgasdg"
    }

    let cart =useFetch("apiGatewayTest@uci.edu",headers,Billing.retrieveCart)?.items;

    useEffect(() => {
        // setCart(response?.items);

        setQuantity(cart?.map(item => {
            return {key: item.movie_id, value: item.quantity}
        }))
    }, [cart]);



    function changeQuantity(key,change){
        setQuantity(quantity.map(
            async q=>{
                if(q.key===key){
                    q.value+=change;
                    if(q.value<1){
                        const response=await Billing.deleteCart(key,"apiGatewayTest@uci.edu",headers);
                        if(response?.data.resultCode==="3120"){
                        }
                        window.location.reload();

                        return;
                    }else{
                        const response=await Billing.updateCart(key,q.value,headers);
                        window.location.reload();
                    }

                }
                return q;
            }
        ))
    }
    const [link,setLink]=React.useState()
    async function checkOut() {
        console.log("checkOut")
        const response = await Billing.placeOrder("apiGatewayTest@uci.edu", headers);
        console.log(response?.data.approve_url)
        setLink(response?.data.approve_url);
    }


    // "items": [
    //     {
    //         "email": "peteranteater@uci.edu",
    //         "unit_price": 10.25,
    //         "discount": 0.95,
    //         "quantity": 2,
    //         "movie_id": "tt4154796",
    //         "movie_title": "Avengers: Endgame"
    //         "backdrop_path": "/7RyHsO4yDXtBv1zUU3mTpHeQ0d5.jpg",
    //         "poster_path": "/or06FN3Dka5tukK1e9sl16pB3iy.jpg",
    //     }, ...
    // ]
    console.log(cart)
    return (
        <div className='cart'>
            <h1 className='text-center'>Cart</h1>
            <a className='btn btn-primary' href={link} onClick={checkOut}>Check Out</a>
            {
                cart?.map(item =>(

                    <div className="card mb-3 "  key={item.movie_id}>
                        <div className="row g-0">
                            <div className="col-md-3 ">
                                <img style={{width:"100%",}} src={`https://image.tmdb.org/t/p/original${item.poster_path}`}/>
                            </div>
                            <div className="col-md-8">
                                <div className="card-body">
                                    <h5 className="card-title">{item.movie_title}</h5>
                                    <p className="card-text">Unit Price: {item.unit_price}</p>
                                    <p className="card-text">Discount: {item.discount}</p>
                                    <div className=''>
                                        <ul className="pagination m-0">
                                            <li className="page-item"><a className="page-link"
                                                                         onClick={()=>changeQuantity(item.movie_id,-1)}>-</a></li>
                                            <li className="page-item">
                                                <a className="page-link" >
                                                    {quantity?.filter(q=>q.key===item.movie_id)[0]?.value}
                                                </a>
                                            </li>
                                            <li className="page-item"><a className="page-link"
                                                                         onClick={()=>changeQuantity(item.movie_id,1)}>+</a></li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                ))
            }


        </div>
    )
}

export default Cart;