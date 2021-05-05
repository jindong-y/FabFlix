import {movieEPs, baseURL} from "../config/config.json";
import Socket from "./Socket";
import getReport from "./gateway";
import Axios from "axios";


async function search(params, headers) {
    console.log("Sending a search request",params)

    const options = {
        baseURL: baseURL,
        url: movieEPs.search,
        headers: headers,
        params: params
    }
    return await getReport(await Axios.get(baseURL+movieEPs.search,{headers: headers, params: params}),"search movie")
    // .then(response => {
    //     if (response.status === 500) {
    //         console.log("Internal Server Error")
    //         return <h1>Internal Server Error</h1>
    //     } else if (response.status === 200) {
    //         return Thumbnails(response.data?.movies.map(movie =>
    //             (movie?.movie_id)
    //         , headers))
    //     } else {
    //         console.log("Connection Error")
    //         return <h1>{response.status}</h1>
    //     }
    // })
    // .catch(error => {
    //     console.log(error)
    //     return <h1>{error}</h1>
    // })
}

async function Thumbnails(movie_ids, headers) {
    console.log("Sending a Thumbnails request")
    const config = {
        baseURL: baseURL,
        url: movieEPs.thumbnail,
        headers: headers,
        data: movie_ids
    }
    return await getReport(await Axios.post(baseURL+movieEPs.thumbnail,movie_ids,{headers: headers}),"thumbnail");


}

async function browse(keywords,params,headers){
    console.log("Sending a browse request",keywords)
    const config={
        baseURL: baseURL,
        url: movieEPs.browse+keywords,
        headers:headers,
        params:params
    }
    return await getReport(await Socket.GET(config),"browse")


}
async function getByID(id,headers ){
    console.log("Sending a get by id request ")
    const config ={
        // baseURL:baseURL,
        // url:movieEPs.getMovieID+id,
        headers:headers
    }
    return await getReport(await Axios.get(baseURL+movieEPs.getMovieID+id,config),"getMovieByID")
}

export default {
    browse,
    search,
    Thumbnails,
    getByID
}