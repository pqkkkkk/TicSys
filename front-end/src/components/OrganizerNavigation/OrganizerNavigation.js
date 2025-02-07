import React from "react";
import { NavLink } from "react-router-dom";
import styles from "./OrganizerNavigation.module.css";
function OrganizerNavigation() {
    return (
        <aside className={styles["left-sidebar"]}>
            <h1 className={styles["logo"]}>eventbee</h1>
            <nav className={styles["nav"]}>
                <ul>
                    <li className={styles["nav-item"]}>
                        <NavLink to="/organizer/create_event" activeClassName={styles["active"]} className={styles["link"]}>Create event</NavLink>
                    </li>
                    
                    <li className={styles["nav-item"]}>
                        <NavLink to="/organizer/events" activeClassName={styles["active"]} className={styles["link"]}>Events</NavLink>
                    </li>
                    <li className={styles["nav-item"]}>
                        <NavLink to="/organizer/profile" activeClassName={styles["active"]} className={styles["link"]}>Profile</NavLink>
                    </li>
                </ul>
            </nav>
        </aside>
    );
}
export default OrganizerNavigation;