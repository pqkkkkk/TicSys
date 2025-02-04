import React from "react";
import { NavLink } from "react-router-dom";
import "./CustomerHeader.css";
import { useNavigate } from "react-router-dom";
import { GetUser } from "../../services/UserStorageService";
import NotificationsIcon from '@mui/icons-material/Notifications';
import LogoutIcon from '@mui/icons-material/Logout';
function CustomerHeader() {
  const navigate = useNavigate();
  const user = GetUser();
  return (
    <header>
      <div className="logo" onClick={() => navigate("/")}>Ticketbox</div>
      <div className="search-bar">
        <input type="text" placeholder="Type name of event..." />
        <button>Search</button>
      </div>
      {user && (
        <div className="become-organizer">
          <button onClick={() => navigate("/become-organizer")}>Become organizer</button>
        </div>
      )}
      <nav>
        <ul>
          {!user && (
              <li>
                <NavLink to="/signin" activeClassName="active" className="link">Sign In</NavLink>
              </li>
          )}
          {user &&(
            <li>
              <NavLink to="/tickets" activeClassName="active" className="link">Tickets</NavLink>
            </li>
          )}
          {user &&(
            <li>
              <NavLink to="/profile" activeClassName="active" className="link">Profile</NavLink>
            </li>
          )}
        </ul>
      </nav>
      <div>
        {user &&(     
              <NotificationsIcon/>
        )}
      </div>
    </header>
  );
}

export default CustomerHeader;