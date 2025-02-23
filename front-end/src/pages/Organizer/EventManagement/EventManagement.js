import React from "react";
import { BrowserRouter as Router, Route, Routes, Link } from "react-router-dom";
import styles from "./EventManagement.module.css";
import { useNavigate } from "react-router-dom";
import Voucher from "./Voucher/Voucher";
import OrderList from "./OrderList/OrderList";
import CreateVoucher from "./CreateVoucher/CreateVoucher";
import Summary from "./Summary/Summary";
function EventManagement() {
    const navigate = useNavigate();

    const HandleReturnOrganizerDashboard = () => {
        navigate("/organizer");
    }
    return (
        <div className={styles["event-management"]}>
            <div className={styles["sidebar"]}>
                <div className={styles["logo"]}>
                    <img src="https://storage.googleapis.com/a1aa/image/egUqrJYZmIVz_juYqi8JRwIOMzTOPPBmL-fKEcOq9Sc.jpg"  alt="Logo"/>
                    <span>Organizer Center</span>
                </div>
                <a onClick={HandleReturnOrganizerDashboard}><i class="fas fa-arrow-left"></i> Organizer Dashboard</a>
                <div>
                    <h2>Report</h2>
                    <Link to={"/organizer/myevents/1/summary"}><i class="fas fa-chart-pie"></i> Summary</Link>
                    <a href="#"><i class="fas fa-bullhorn"></i> Analysis</a>
                    <Link to={"/organizer/myevents/1/order-list"}><i class="fas fa-list"></i> Order List</Link>
                </div>
                <div>
                    <h2>Event setting</h2>
                    <a href="#"><i class="fas fa-user"></i> Co-member</a>
                    <a href="#"><i class="fas fa-edit"></i> Edit </a>
                    <a href="#"><i class="fas fa-map-marker-alt"></i> SeatMap</a>
                </div>
                <div>
                    <h2>Marketing</h2>
                    <Link to={"/organizer/myevents/1/voucher"}><i class="fas fa-cog"></i> Promotion</Link>
                </div>
            </div>
            <div className={styles["content-field"]}>
                <div className={styles["title"]}>
                    <h1>My Event</h1>
                    <div className={styles["divider"]}></div>
                </div>
                <div className={styles["main-content"]}>
                    <Routes>
                        <Route path="voucher" element={<Voucher/>}/>
                        <Route path="order-list" element={<OrderList/>}/>
                        <Route path="create-voucher" element={<CreateVoucher/>}/>
                        <Route path="summary" element={<Summary/>}/>
                    </Routes>
                </div>
            </div>
        </div>
    );
}

export default EventManagement;