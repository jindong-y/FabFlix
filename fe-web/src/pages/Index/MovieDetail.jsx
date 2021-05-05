import Movie from "../../services/Movie";
import React from "react"
import useFetch from "../../hooks/useFetch"
import './menu-bar.css'
import Billing from '../../services/Billing'

function MovieDetail({location}) {
    console.log("Render detail")

    console.log(location)
    const array = location.pathname.split("/")
    const movie_id = array[array.length - 1];

    const headers = {
        // email: localStorage.get("email"),
        // session_id: localStorage.get("session_id"),
        email: "apiGatewayTest@uci.edu",
        session_id: "d0235ee6d5701c537ff0803c1ae68252dcf0e0cd59ffa19391609d6802b4403470800e932e0caf59082ad4b9ca6840a080f3efee0103902ebe8c9b2b74a75bad",
        // transaction_id: "dsafasdgasdg"
    }

    const movie = useFetch(movie_id, headers,Movie.getByID)?.movie;
    console.log(movie)

    function ratingBg(rating){
        if(rating>=7.5){
            return "bg-success"
        }else if( rating>=6){
            return "bg-warning"
        }else return "bg-danger"
    }

    const [amount,setAmount]=React.useState(1);

    const addToCart=()=>{
        Billing.insert(movie_id,amount,headers)
            .then(response=>{
                // console.log(response)
                alert(response?.data.message)
            })
            .catch(error=>alert(error))
    }


    return (
        <>

            {movie ?
                <div className=" detail " style={{
                    "background-image": `linear-gradient(rgba(0, 0, 0, 0.8),
                rgba(0, 0, 0, 0.8)), url('https://image.tmdb.org/t/p/original${movie?.backdrop_path}')`,
                    "background-size": "cover"
                }}>
                    <div className="overflow-scroll">
                        <img src={`https://image.tmdb.org/t/p/original${movie?.poster_path}`}
                             className="col-4 m-3" id="poster"/>
                        <div className="col-6 my-3">
                            <div>
                                <h2 className="text-white ">{movie.title}</h2>
                                <span className={`badge ${ratingBg(movie.rating)} m-3`}>{movie.rating}</span>

                            </div>
                            <div>
                                <p className='text-white'>{movie.year}</p>

                            </div>
                            <p className='text-white'>Director: {movie.director}<br/>
                                Votes: {movie.num_votes}<br/>Budget: ${movie.budget}<br/>
                                Revenue: ${movie.revenue}
                            </p>

                        </div>

                        <p className='text-white mx-4'>{movie.overview}</p>


                            <div className='text-white mx-4 cast' style={{height:"4em"}}>
                                <p className='text-white ' >Genre:
                                {movie.genres?.map(genre =>
                                    <span className='text-white' key={movie.genres.genre_id}>
                                        <a>{genre.name}, </a>
                                    </span>
                                )}


                                <br/>Cast:
                                {movie?.people?.map(person =>
                                    <span className='text-white'  key={movie.people.person_id}>
                                        <a>{person.name}, </a>
                                    </span>
                                )}
                                </p>
                            </div>

                    </div>
                    <div className='d-flex justify-content-around'>
                        <div className='my-4 col-3'>
                                <ul className="pagination m-0">
                                    <li className="page-item"><a className="page-link" onClick={()=>setAmount(amount==1?1:amount-1)}>-</a></li>
                                    <li className="page-item"><a className="page-link" >{amount}</a></li>
                                    <li className="page-item"><a className="page-link" onClick={()=>setAmount(amount+1)}>+</a></li>
                                </ul>
                        </div>
                    <a className="col-5 my-4 btn btn-primary" onClick={addToCart}>Add to cart</a>
                    </div>
                </div>


                // // <div>
                //        {/*<h1>{movie.title}</h1>*/}
                //        {/*<h3>{movie.year}</h3>*/}
                //        {/*<h3>{movie.director}</h3>*/}
                //        {/*<h3>{movie.rating}</h3>*/}
                //        {/*<h3>{movie.num_votes}</h3>*/}
                //        {/*<h3>{movie.budget}</h3>*/}
                //        {/*<h3>{movie.revenue}</h3>*/}

                //
                //    // </div>
                : <div>wait....</div>}


        </>
    )
}

export default MovieDetail;