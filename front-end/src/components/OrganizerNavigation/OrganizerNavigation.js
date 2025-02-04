import React from "react";
import { NavLink } from "react-router-dom";
import "./OrganizerNavigation.css";
function OrganizerNavigation() {
    return (
        <aside className="left-sidebar">
            <h1 className="logo">eventbee</h1>
            <nav className="nav">
                <ul>
                    <li className="nav-item">
                        <NavLink to="/organizer/create_event" activeClassName="active" className="link">Create event</NavLink>
                    </li>
                    <li className="nav-item">
                        <NavLink to="/organizer/events" activeClassName="active" className="link">Events</NavLink>
                    </li>
                    <li className="nav-item">
                        <NavLink to="/organizer/profile" activeClassName="active" className="link">Profile</NavLink>
                    </li>
                </ul>
            </nav>
        </aside>
    );
}
export default OrganizerNavigation;