import React, {useState} from "react";

const SessionContext =React.createContext(null);



export const SessionProvider =({children})=>{
    const [session,setSession] =useState();
    //TODO default isLoggedIn is false
    const [isLoggedIn, setIsLoggedIn]=useState(true);
    const value={session, setSession,isLoggedIn, setIsLoggedIn};
    return (
    <SessionContext.Provider value={value}>
        {children}
    </SessionContext.Provider>
    )
}

export const useSession= ()=>{
    const {session, setSession,isLoggedIn, setIsLoggedIn}=React.useContext(SessionContext);
    return {session, setSession,isLoggedIn, setIsLoggedIn};
}

