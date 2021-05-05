import Socket from "./Socket";
import { idmUrl, idmEPs } from "../config/config.json";
async function login(email, password) {
    if(!email||!password){
        throw "Please enter email and password";
    }
    const payLoad = {
        email: email,
        password: password.split("")
    };

    const options = {
        baseURL: idmUrl, // Base URL
        url: idmEPs.loginEP, // Path of URL
        data: payLoad // Data to send in Body
    }

    return await Socket.POST(options);
}


async function register(email, password){
    const payload ={
        email: email,
        password: password
    }
    const options ={
        baseURL: idmUrl,
        url: idmEPs.registerEP,
        data: payload
    }
    return await Socket.POST(options);

}




export default {
    login,
    register
};