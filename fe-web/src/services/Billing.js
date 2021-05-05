import {baseURL, billingEPs} from "../config/config.json";
import getReport from "./gateway";
import Socket from "./Socket";


async function insert(movie_id, quantity, headers ){
    console.log("Sending a insert to cart request ")
    const config ={
        baseURL:baseURL,
        url:billingEPs.cartInsert,
        headers:headers,
        data:{
            email: headers.email,
            movie_id:movie_id,
            quantity:quantity
        }
    }
    return  getReport(await Socket.POST(config),"insertToCart")
}

async  function retrieveCart(email,headers){
    console.log("Sending a retrieveCart request")
    const config={
        baseURL:baseURL,
        url:billingEPs.cartRetrieve,
        headers:headers,
        data:{
            email:email
        }
    }
    return getReport(await Socket.POST(config,"retrieveCart"))
}
async function updateCart(movie_id, quantity, headers ){
    console.log("Sending a update to cart request ")
    const config ={
        baseURL:baseURL,
        url:billingEPs.cartUpdate,
        headers:headers,
        data:{
            email: headers.email,
            movie_id:movie_id,
            quantity:quantity
        }
    }
    return  getReport(await Socket.POST(config),"updateCart")
}
async function deleteCart(movie_id, email, headers ){
    console.log("Sending a delete item request ")
    const config ={
        baseURL:baseURL,
        url:billingEPs.cartDelete,
        headers:headers,
        data:{
            email: email,
            movie_id:movie_id
        }
    }
    return  getReport(await Socket.POST(config),"cartDelete")
}

async  function placeOrder(email,headers){
    console.log("Sending a place order request")
    const config={
        baseURL:baseURL,
        url:billingEPs.orderPlace,
        headers:headers,
        data:{
            email: email
        }

    }
    return getReport(await Socket.POST(config),"placeOrder");
}

async  function orderRetrieve(email,headers){
    console.log("Sending a getOrder request")
    const config={
        baseURL:baseURL,
        url:billingEPs.orderRetrieve,
        headers:headers,
        data:{
            email: email
        }

    }
    return getReport(await Socket.POST(config),"orderRetrieve");
}

async function orderComplete(params,headers){
    console.log("Sending a orderComplete request")
    const config={
        baseURL:baseURL,
        url:billingEPs.orderComplete+params,
        headers:headers
    }
    return getReport(await Socket.GET(config),"orderComplete");
}

export default {insert,retrieveCart,updateCart,deleteCart,placeOrder,orderRetrieve,orderComplete};