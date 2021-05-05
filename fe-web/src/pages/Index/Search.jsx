import React,{useEffect} from "react";
import Axios from "axios";
import Movie from "../../services/Movie"
import NavBar from "../../app/NavBar";
import "./menu-bar.css"
import {Switch, Route} from "react-router-dom";
import Cart from "./Cart"
import Order from "./Order";

const Search = ({history, match}) => {
    const [filter, setFilter] = React.useState("title");
    const [search_txt, setSearch] = React.useState();

    const handleOptionChange = (props) => {
        console.log(props)
        setFilter(props.target.value)
    }

    const handleSubmit = (e) => {
        e.preventDefault();
        if(search_txt==null)return;
        console.log("go to movies_list page")
        if (filter === 'year' && !parseInt(search_txt)) {
            return alert("year must be a number")
        }
        history.push("/index/search")


    }
    const Search = () => {
        return <div className="container-lg">
            <h1>Search</h1>
            <div>
                <form className='container-lg border border-5 rounded rounded-3' onSubmit={handleSubmit}>
                    <div className='row'>
                        <input className="col-7 m-3 form-control" type="text"
                               onChange={(e) => setSearch(e.target.value)}/>
                        <button className='col-3 m-3 btn btn-primary'>Search</button>
                    </div>
                    <div className='row'>
                        <label className="col-sm-3 form-label">
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
        </div>
    }
    // axios.post('https://reqres.in/api/articles', article, { headers })
    // Axios.get('http://127.0.0.1:12345/api/movies/search?title=10',{headers})
    //
    return (
        // isLoggedIn?
        <>
            <Switch>

                <Route path="/index/cart">
                    <Cart/>
                </Route>
                <Route path="/index/order">
                    <Order/>
                </Route>
                <Route path="/">
                    {Search()}
                </Route>
            </Switch>

        </>
        // :<></>
    );
}


export default Search;
