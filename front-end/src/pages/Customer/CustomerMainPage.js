import {BrowserRouter as Router, Route, Routes} from "react-router-dom";
import CustomerHeader from "../../components/CustomerHeader/CustomerHeader";
import CustomerDashboard from "./Dashboard/CustomerDashboard";
import React from "react";
import EventDetail from "./EventDetail/EventDetail";

function CustomerMainPage() {
  return (
        <div>
            <CustomerHeader/>

            <Routes>
                <Route  path="tickets" Component={CustomerDashboard}/>
                <Route path="profile" Component={CustomerDashboard}/>
                <Route exact path="" Component={CustomerDashboard}/>
                <Route path="/:eventId" Component={EventDetail}/>
            </Routes>
        </div>
  );
}

export default CustomerMainPage;