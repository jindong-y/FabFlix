import React, {useState} from "react";

import Content from "./Content";
import NavBar from "./NavBar";
import 'bootstrap/dist/css/bootstrap.min.css';

/*
  Remember when passing around variable and functions

  If you want components down the tree to VIEW your
  variable, simply pass that variable

  If you want components down the tree to MODIFY your
  variable, then pass a function down the tree that allows
  the components to modify it as needed

  The function should be made in the Component that "owns" that 
  variable, as in which Component has it as part of its state
  because only that component can modify its own state.
  Pass variables and functions into Components like so:

    <Component var={value} func={value}/>

  IMPORTANT: DO NOT CALL THE FUNCTION

  func={function}   WORKS
  func={function()} DOES NOT WORK, This is calling the function 
                                   and storing the return value

  To get the passed variables and functions from inside 
  the Component call `this.props.<NAME>`
*/

// const [isLoggedIn, setIsLoggedIn]=useState(false);

const App = () => {
    const [isLoggedIn, setIsLoggedIn]=useState(false);
    return (
        <div className="app">
            <NavBar {...{isLoggedIn, setIsLoggedIn}}/>
            <Content {...{isLoggedIn, setIsLoggedIn}}/>
        </div>
    );
}

export default App;
