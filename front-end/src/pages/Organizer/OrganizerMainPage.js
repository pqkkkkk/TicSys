import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import OrganizerNavigation from "../../components/OrganizerNavigation/OrganizerNavigation";
import Events from "./Events/Events";
import "./OrganizerMainPage.css";
import CreateEvent from "./CreateEvent/CreateEvent";

function OrganizerMainPage() {
  return (
      <div className="container">
        <OrganizerNavigation />
        
        <div className="content">
          <Routes>
            <Route path="create_event" Component={CreateEvent} />
            <Route path="events" Component={Events} />
            <Route path="profile" Component={Events} />
          </Routes>
        </div>
      </div>
  );
}

export default OrganizerMainPage;