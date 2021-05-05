import Socket from "./Socket";
import { baseURL, idmEPs } from "../config/config.json";
import getReport from "./gateway";
import Axios from "axios";


async function login(email, password) {
    if(!email||!password){
        throw "Please enter email and password";
    }
    const payLoad = {
        email: email,
        password: password.split("")
    };

    const options = {
        baseURL: baseURL, // Base URL
        url: idmEPs.login, // Path of URL
        data: payLoad // Data to send in Body
    }

    console.log(options)
    // return await getReport(await Socket.POST(options),"login");

    return await getReport(await Axios.post(baseURL+idmEPs.login,options.data),"login");
}


async function register(email, password){
    const payload ={
        email: email,
        password: password
    }
    const options ={
        baseURL: baseURL,
        url: idmEPs.register,
        data: payload
    }
    return await getReport(await Axios.post(baseURL+idmEPs.register,options.data),"register");

}




export default {
    login,
    register
};