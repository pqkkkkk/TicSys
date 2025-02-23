import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import OrganizerNavigation from "../../components/OrganizerNavigation/OrganizerNavigation";
import styles from "./OrganizerMainPage.module.css"
import CreateEvent from "./CreateEvent/CreateEvent";
import MyEventList from "./MyEventList/MyEventList";
function OrganizerMainPage() {
  return (
      <div className={styles["container"]}>
        <OrganizerNavigation />
        
        <div className={styles["content"]}>
          <Routes>
            <Route path="create_event" Component={CreateEvent} />
            <Route path="events" Component={MyEventList} />
            <Route path="profile" Component={MyEventList} />
          </Routes>
        </div>
      </div>
  );
}

export default OrganizerMainPage;