import {BrowserRouter as Router, Route, Routes} from "react-router-dom";
import CustomerHeader from "../../components/CustomerHeader/CustomerHeader";
import CustomerDashboard from "./Dashboard/CustomerDashboard";
import React from "react";
import { createTheme } from "@mui/material";
import { ThemeProvider } from "@emotion/react";

function CustomerMainPage() {
  return (
        <div>
            <CustomerHeader/>

            <Routes>
                <Route  path="tickets" Component={CustomerDashboard}/>
                <Route path="profile" Component={CustomerDashboard}/>
                <Route exact path="" Component={CustomerDashboard}/>
            </Routes>
        </div>
  );
}

export default CustomerMainPage;