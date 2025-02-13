import {BrowserRouter as Router, Route, Routes} from "react-router-dom";
import CustomerHeader from "../../components/CustomerHeader/CustomerHeader";
import CustomerDashboard from "./Dashboard/CustomerDashboard";
import React from "react";
import EventDetail from "./EventDetail/EventDetail";
import styles from "./CustomerMainPage.module.css";
import SelectTicket from "./Booking/SelectTicket/SelectTicket";
import QuestionForm from "./Booking/QuestionForm/QuestionForm";
import PaymentInfo from "./Booking/PaymentInfo/PaymentInfo";
import MyTicket from "./MyTicket/MyTicket";
function CustomerMainPage() {
  return (
        <div className={styles["container"]}>
            <CustomerHeader/>
            <Routes>
                <Route  path="tickets" Component={MyTicket}/>
                <Route path="profile" Component={CustomerDashboard}/>
                <Route exact path="" Component={CustomerDashboard}/>
                <Route path="/:eventId" Component={EventDetail}/>
                <Route path="/booking/:eventId/select-ticket" Component={SelectTicket}/>
                <Route path="/booking/:eventId/question-form/:orderId" Component={QuestionForm}/>
                <Route path="/booking/:eventId/payment-info/:orderId" Component={PaymentInfo}/>
            </Routes>
        </div>
  );
}

export default CustomerMainPage;