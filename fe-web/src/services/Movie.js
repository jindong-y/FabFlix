import {movieEPs, movieUrl} from "../config/config.json";
import Socket from "./Socket";



async function search(title,year,director,headers){
    let url= movieEPs.searchEP;
    // if(title){
    //     url=url+`?title=${title}`
    // }
    console.log(url+title)

    url=url+(title?`?title=${title}`:"")+(year?`?year=${year}`:"")+(director?`?director=${director}`:"");
    console.log(url)
    const options ={
        baseURL: movieUrl,
        url: url,
        headers:headers
    }
    return await Socket.GET(options);
}

export default{
    search
}