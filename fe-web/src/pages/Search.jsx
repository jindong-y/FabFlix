import React from "react";
import Axios from "axios";
import Movie from "../services/Movie"
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

const Search = ({history, location, match,isLoggedIn}) => {
    const [filter, setFilter] = React.useState("title");
    const [search_txt, setSearch] = React.useState();
    const [results, setResults] = React.useState();
    const handleOptionChange = (props) => {
        console.log(props)
        setFilter(props.target.value)
    }

    const handleSubmit = (e) => {

        e.preventDefault();
        const headers = {
            email: localStorage.get("email"),
            session_id: localStorage.get("session_id"),
            transaction_id: "dsafasdgasdg"
        }
        console.log(headers)
        if(filter==='year'&&!parseInt(search_txt)){
            return alert("year must be a number")
        }
        const title = filter === 'title' ? search_txt : "";
        const year = filter === 'year' ? search_txt : "";
        const director = filter === 'director' ? search_txt : "";
        Movie.search(title, year, director, headers)
            .then(response => {
                // alert(JSON.stringify(response.data, null, 4))
                // console.log(response.data)
                // console.log(response.data.movies)
                setResults(response.data?.movies?.map(movie => {
                    return {key: movie.movie_id, ...movie}
                }))
            })
            .catch(()=>setResults())
        console.log(results)
    }


    // axios.post('https://reqres.in/api/articles', article, { headers })
    // Axios.get('http://127.0.0.1:12345/api/movies/search?title=10',{headers})
    //
    return (
        isLoggedIn?
        <div className="container-lg">
            <h1>Search</h1>
            <div>
                <form className='container-lg border border-5 rounded rounded-3' onSubmit={handleSubmit}>
                    <div className='row'>
                        <input className="col-7 m-3 form-control" type="text"
                               onChange={(e) => setSearch(e.target.value)}/>
                        <button className='col-3 m-3 btn btn-primary'>Search</button>
                    </div>
                    <div className='row'>
                        <label className="col-sm-3 form-label" >
                            <input className="m-2 form-check"
                                   type="radio" name="filter" value="title"
                                   checked={filter === "title"}
                                   onChange={handleOptionChange}
                            />
                            Title
                        </label>
                        <label className="col-sm-3 form-label">
                            <input className="m-2 form-check"
                                   type="radio" name="filter" value="year"
                                   checked={filter === "year"}
                                   onChange={handleOptionChange}
                            />
                            Year
                        </label>
                        <label className="col-sm-3 form-label">
                            <input className="m-2 form-check"
                                   type="radio" name="filter" value="director"
                                   checked={filter === "director"}
                                   onChange={handleOptionChange}
                            />
                            Director
                        </label>

                    </div>
                </form>
            </div>
            <Results results={results}/>
        </div>
            :<></>
    );
}


const Results = ({history, location, match, results}) => {
    return (
        <>
            <table className='table'>
                <tr className='table-info'>
                    <th>Title</th>
                    <th>Year</th>
                    <th>Director</th>
                </tr>
                {results?.map(movie => {
                    return <tr className='table-active'>
                        <td>{movie?.title}</td>
                        <td>{movie?.year}</td>
                        <td>{movie?.director}</td>
                    </tr>
                })}

            </table>
        </>
    );
}

export default Search;
