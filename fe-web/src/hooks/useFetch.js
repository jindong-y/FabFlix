import {useEffect, useState} from "react";
import Movie from "../services/Movie";

function useFetch(params,headers,request){
    const [response,setResponse]= useState();

    useEffect( async ()=> {
        const response= await request(params,headers);
        setResponse(response?.data);
    },[params])

    return response;
}
export default useFetch;