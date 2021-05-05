import React, {useState} from "react";
import Idm from "../services/Idm"
import {NavLink} from "react-router-dom";
import {Link} from "react-router-dom";
import {useSession} from "../hooks/session";
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

const Login = (props) => {
    // The Top ({ history, location, match }) is a short hand for the following code:
    //     const Login = (props) => {
    //         const { history, location, match } = props;
    // If you want to accept more props just place it there like this:
    //     const Login = ({ history, location, match, yourVar }) => {
    console.log(props)

    const {history, location, match}=props
    const [email, setEmail] = useState();
    const [password, setPassword] = useState();
    const {setIsLoggedIn}=useSession();

    const handleSubmit = (e) => {
        e.preventDefault();

        Idm.login(email, password)
            .then(response => loginSuccess(response))
            .catch(error => alert(error));
    };
    // alert(JSON.stringify(response.data, null, 4))
    const loginSuccess = (response)=>{
        const {resultCode, message, session_id}=response.data;
        console.log(response.data);
        if(resultCode===120){
            setIsLoggedIn(true);

            localStorage.set("session_id",session_id);
            localStorage.set("email",email)
            console.log(localStorage.get("email"))
            console.log(localStorage.get("session_id"))

            history.push("/index");
        }else{
            alert(message);
        }
    }



    return (
        <div className="form-box">
            <h1>Login</h1>
            <form onSubmit={handleSubmit}>
                <label className="form-label">Email</label>
                <input
                    className="form-input"
                    type="email"
                    onChange={(e) => setEmail(e.target.value)}
                />
                <label className="form-label">Password</label>
                <input
                    className="form-input"
                    type="password"
                    onChange={(e) => setPassword(e.target.value)}
                />
                <div>
                    <button className="form-button">Login</button>
                    <Link className="form-button" onClick={()=>history.push("/register")}>New User</Link>
                </div>
            </form>
        </div>
    );
}

export default Login;
