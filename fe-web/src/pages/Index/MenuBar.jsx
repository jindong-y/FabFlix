import {NavLink} from "react-router-dom";
import './menu-bar.css'
const MenuBar=()=>{
    return <div className="menu-bar">
            <NavLink  to="/index">
                Index
            </NavLink>
    </div>;
}
export default MenuBar;