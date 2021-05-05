import React, {useEffect} from "react";
import Axios from "axios";
import Movie from "../../services/Movie"
import NavBar from "../../app/NavBar";
import "./menu-bar.css"
import {Switch, Route} from "react-router-dom";
import Cart from "./Cart"
import Order from "./Order";
import MovieDetail from "./MovieDetail";
import OrderComplete from "./OrderComplete";
/*
  Using localStorage is similar to how we use
  dictionary.
  
  To set a variable call `localStorage.set("key", value)`
  To get a variable call `localStorage.get("key")`

  Local Storage persists through website refreshes so
  it is perfect for storing things we dont want to lose
  like a users session

  You must call `const localStorage = require("local-storage");`
  in any class that you want to use this in, it is the same
  local storage in the entire website regardless of where you call
  it as each website gets the same instance of the storage.

  So think of it as a global dictionary.
*/
const localStorage = require("local-storage");

const Index = ({history, location, match}) => {
    const [filter, setFilter] = React.useState("title");
    const [title, setTitle] = React.useState();
    const [year, setYear] = React.useState();
    const [director, setDirector] = React.useState();
    const [genre, setGenre] = React.useState();
    const [keywords, setKeywords] = React.useState();
    const [order,setOrder] = React.useState("title")
    const [direction, setDirection] = React.useState("asc")

    const [results, setResults] = React.useState();
    const [posterResults, setPosterResults] = React.useState();
    const [currentPage, setCurrentPage] = React.useState(1)
    const [offset, setOffset] = React.useState(1);
    const [pagination, setPagination] = React.useState([])
    const [isSearch, setIsSearch] = React.useState(true);
    const [hasMovie,setHasMovie] = React.useState(true);


    let movieAmount = 0;

    useEffect(() => {
        getThumbnailsList(results?.map(result => result?.movie_id), null)
            .then(res => {
                setPosterResults(res);
            })
    }, [results])

    const handleOptionChange = (props) => {
        setFilter(props.target.value)
    }

    const handleSubmit = async (e) => {
        console.log("search submit triggered")
        e.preventDefault();
        const movies = await getMovie(100, 0);

        if(!movies){
            setHasMovie(false);
            return;
        }
        setHasMovie(true);
        console.log("setMovieAmount", movies.length)
        movieAmount = movies.length;
        setPagination(() => {
                let pages = [];
                for (let i = 0; i < Math.ceil(movieAmount / 10); i++) {
                    pages[i] = (
                        {
                            key: i + 1
                        }
                    )
                }
                return pages;
            }
        )
        setResults(movies.slice(0, 10))
        console.log("movies size>=", movies.length)
        setCurrentPage(1);
    }

    async function getMovie(limit = 10, offset = 0) {
        const headers = {
            // email: localStorage.get("email"),
            // session_id: localStorage.get("session_id"),
            email: "apiGatewayTest@uci.edu",
            session_id: "d0235ee6d5701c537ff0803c1ae68252dcf0e0cd59ffa19391609d6802b4403470800e932e0caf59082ad4b9ca6840a080f3efee0103902ebe8c9b2b74a75bad",
            // transaction_id: "dsafasdgasdg"
        }
        let response;
        if (year && !parseInt(year)) {
            return alert("year must be a valid number")
        }
        let params = {};
        if(isSearch) {
            console.log("search by title....")
            if (title) params["title"] = title;
            if (year) params["year"] = year;
            if (director) params["director"] = director;
            if (genre) params["genre"] = genre;
            //if no enter
            if (Object.keys(params).length===0) {
                console.log("no params")
                return []
            };
            params["orderby"]=order;
            params["direction"]=direction;
            params["limit"] = limit;
            params["offset"] = offset;
             response = await Movie.search(params, headers)

        }else {

            console.log("browse by keywords")
            params["orderby"]=order;
            params["direction"]=direction;
            params["limit"] = limit;
            params["offset"] = offset;
            response=await Movie.browse(keywords,params,headers);

        }

        console.log(response.data?.movies)
        return response.data?.movies;
    }

    const Search = () => {
        return <div className="container-lg">
            <h1 style={{textAlign: 'center'}}>Search for movies</h1>
            <div>
                <form className='container-lg border border-5 rounded rounded-3' onSubmit={handleSubmit}>
                    {isSearch ? <div>
                            <div className='row'>
                                <label htmlFor="title" className="col-2 my-3 mx-2 text-center col-form-label">Title</label>
                                <input className="col-4 my-3 me-2 form-control" type="text" id="Title"
                                       onChange={(e) => setTitle(e.target.value)}/>
                                <label htmlFor="Year" className="col-2 my-3 text-center col-form-label">Year</label>
                                <input className="col-3 my-3 form-control" type="text" id="Year"
                                       onChange={(e) => setYear(e.target.value)}/>
                            </div>

                            <div className='row'>
                                <label htmlFor="Director"
                                       className="col-2 my-3 mx-2 text-center col-form-label">Director</label>
                                <input className="col-4 my-3 form-control" type="text" id="Director"
                                       onChange={(e) => setDirector(e.target.value)}/>
                                <label htmlFor="Genre" className="col-2 my-3 text-center col-form-label">Genre</label>
                                <input className="col-3 my-3 form-control" type="text" id="Genre"
                                       onChange={(e) => setGenre(e.target.value)}/>
                            </div>
                        </div>
                        :
                        <div className='row'>
                            <label htmlFor="Keywords" className="col-3 my-3 ms-2  col-form-label">Keywords</label>
                            <input className="col-8 my-3 form-control" type="text" id="Keywords" placeholder="keywords separated by commas"
                                   onChange={(e) => setKeywords(e.target.value)}/>
                        </div>
                    }
                    <div className='row'>
                        <label htmlFor='orderBy' className='col-2.5 my-3 mx-2 text-center '> Sorted by</label>
                        <select className="col-1.5  my-3 px-0 form-select text-center" id="orderBy"
                                onChange={(e)=>setOrder(e.target.value)}>
                            <option selected value="title">Title</option>
                            <option value="year">Year</option>
                            <option value="rating">Rating</option>
                        </select>
                        <select className="col-1.5  my-3 m-auto ps-0 form-select text-center" id="direction"
                                onChange={(e)=>setDirection(e.target.value)}>
                            <option selected value="asc">asc</option>
                            <option value="desc">desc</option>
                        </select>
                        <button className='col-5 m-2 btn btn-primary'>Search</button>
                    </div>

                    <div className="form-check  col-5 ">
                        <input className="form-check-input" type="checkbox" id="keywordsCheck"
                               onChange={() => setIsSearch(!isSearch)}/>
                        <label className="form-check-label" htmlFor="keywordsCheck">
                            Keywords Search
                        </label>

                    </div>

                    {/*<div className='row'>*/}
                    {/*    <label className="col-sm-3 form-label">*/}
                    {/*        <input className="m-2 form-check"*/}
                    {/*               type="radio" name="filter" value="title"*/}
                    {/*               checked={filter === "title"}*/}
                    {/*               onChange={handleOptionChange}*/}
                    {/*        />*/}
                    {/*        Title*/}
                    {/*    </label>*/}
                    {/*    <label className="col-sm-3 form-label">*/}
                    {/*        <input className="m-2 form-check"*/}
                    {/*               type="radio" name="filter" value="year"*/}
                    {/*               checked={filter === "year"}*/}
                    {/*               onChange={handleOptionChange}*/}
                    {/*        />*/}
                    {/*        Year*/}
                    {/*    </label>*/}
                    {/*    <label className="col-sm-3 form-label">*/}
                    {/*        <input className="m-2 form-check"*/}
                    {/*               type="radio" name="filter" value="director"*/}
                    {/*               checked={filter === "director"}*/}
                    {/*               onChange={handleOptionChange}*/}
                    {/*        />*/}
                    {/*        Director*/}
                    {/*    </label>*/}

                    {/*</div>*/}
                </form>
            </div>
            {hasMovie ?<>
                <PosterList results={posterResults}/>
                {/*<ResultList results={results}/>*/}
                <Pagination/>
            </>
                :<h2>No results found</h2>
            }
        </div>
    }


    function Pagination() {
        console.log("render pagination")
        console.log("CurrentPage", currentPage)
        console.log("pagination", pagination)
        let pages = pagination;

        // console.log("initial pagination",pagination)
        async function goToPreviousPage() {
            console.log("go to pre")
            setCurrentPage(currentPage - 1);
            if (pagination.length === 10) {
                if (currentPage >= 6) {

                    console.log("update page",)
                    pages.forEach((page) => {
                        page.key--;
                    })
                    setPagination(pages)
                    console.log("pages", pages)
                }
            }
            const movies = await getMovie(10, 10 * currentPage - 20);
            setResults(movies);
            setCurrentPage(currentPage - 1);
        }

        async function goToNextPage() {
            console.log("go to next page");
            console.log(pagination.length)
            //update pagination
            if (pagination.length === 10) {
                if (currentPage >= (pages[0].key + pages[9].key) / 2 - 1) {
                    const movies = await getMovie(10, 10 * pages[9].key);
                    if (movies && movies.length !== 0) {
                        console.log("update page", pages[9].key)
                        pages.forEach((page) => {
                            page.key++;
                        })
                        setPagination(pages)
                        console.log("pages", pages)
                    }
                }
            }
            const movies = await getMovie(10, 10 * currentPage);
            setResults(movies);
            setCurrentPage(currentPage + 1);

        }

        return (
            <div style={{display: "flex", justifyContent: "center"}}>
                {(currentPage !== 1) && <button onClick={goToPreviousPage}>Prev</button>}
                {pagination?.map((page) => {
                    return <div key={page?.key}>
                        <button style={{"color": page?.key === currentPage ? "red" : "blue"}} className='pagination'>
                            {page?.key}
                        </button>
                    </div>
                })}
                {((pages.length !== 0) && (currentPage !== pages[pages.length - 1]?.key)) &&
                <button onClick={goToNextPage}>Next</button>}

            </div>
        )
    }

    return (
        // isLoggedIn?
        <>
            <Switch>

                <Route path="/index/cart">
                    <Cart/>
                </Route>
                <Route path="/index/order/complete">
                    {props => <OrderComplete {...props}/>}
                </Route>
                <Route path="/index/order">
                    {props => <Order {...props}/>}
                </Route>
                <Route path="/index/movie">
                    {props => <MovieDetail {...props}/>}
                </Route>
                <Route path="/">
                    {Search()}
                </Route>
            </Switch>

        </>
        // :<></>
    );
}


async function getThumbnailsList(movie_ids, headers) {
    if (movie_ids == undefined) {
        return null;
    }
    console.log("Try to get thumbnail List");
    console.log("movie_ids", movie_ids)
    headers = {
        // email: localStorage.get("email"),
        // session_id: localStorage.get("session_id"),
        email: "apiGatewayTest@uci.edu",
        session_id: "d0235ee6d5701c537ff0803c1ae68252dcf0e0cd59ffa19391609d6802b4403470800e932e0caf59082ad4b9ca6840a080f3efee0103902ebe8c9b2b74a75bad",
        // transaction_id: "dsafasdgasdg"
    }
    let result = null;
    const response = await Movie.Thumbnails({movie_ids: movie_ids}, headers)

    if (response.status === 200) {
        console.log("got thumbnail response");
        result = response.data?.thumbnails;
    }
    console.log(result)
    return result;
}

function PosterList({results}) {
    return (
        <div className='thumbnail'>
            {results?.map(result =>
                <div className='' key={result?.movie_id}>
                    <img src={`https://image.tmdb.org/t/p/original${result?.poster_path}`}/>
                    <a href={`/index/movie/${result?.movie_id}`}>{result?.title.length > 25 ? result?.title.slice(0, 25) + "..." : result?.title}</a>
                </div>
            )}
        </div>
    );

}

const ResultList = ({history, location, match, results}) => {
    console.log("render movie list.")
    return (
        <>
            <table className='table'>
                <tbody>
                <tr className='table-info'>
                    <th>Title</th>
                    <th>Year</th>
                    <th>Director</th>
                </tr>
                {results?.map(movie => {
                    return <tr className='table-active' key={movie?.movie_id}>
                        <td>{movie?.movie_id}</td>
                        <td>{movie?.title}</td>
                        <td>{movie?.year}</td>
                        <td>{movie?.director}</td>
                    </tr>
                })}
                </tbody>

            </table>
        </>
    );
}

export default Index;
