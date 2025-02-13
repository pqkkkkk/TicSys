import React from "react";
import styles from "./MyTicket.module.css";
import { useState, useEffect } from "react";
import { GetFilteredOrdersApi } from "../../../services/api/OrderApi";
import { GetUser } from "../../../services/UserStorageService";
import {useNavigate} from "react-router-dom";
function MyTicket() {
    const user = GetUser();
    const navigate = useNavigate();

    const [orders, setOrders] = useState([]);
    
    useEffect(() => {
        if(!user)
        {
            navigate("/signin");
        }
        else{
            GetFilteredOrdersApi({userId: user.id}).then((response) => {
                setOrders(response);
            }).catch((error) => {
                console.log(error);
            });
        }
    }, [user]);
    return (
        <div className={styles["container"]}>
            <h1 className={styles["header"]}>My Tickets</h1>
            <div className={styles["tabs"]}>
                <div className={styles["tab"] + " " + styles["active"]}>All</div>
                <div className={styles["tab"] + " " + styles["inactive"]}>Finished</div>
                <div className={styles["tab"] + " " + styles["inactive"]}>Processing</div>
                <div className={styles["tab"] + " " + styles["inactive"]}>Cancelled</div>
            </div>
            <div className={styles["sub-tabs"]}>
                <div className={styles["sub-tab"] + " " + styles["active"]}>Upcoming</div>
                <div className={styles["sub-tab"] + " " + styles["inactive"]}>Past</div>
            </div>
            <div className={styles["ticket"]}>
                <div className={styles["ticket-date"]}>
                    <p className={styles["day"]}>16</p>
                    <p className={styles["month"]}>February</p>
                    <p className={styles["year"]}>2025</p>
                </div>
                <div className={styles["ticket-details"]}>
                    <h2>NGÀY AN LÀNH - khoá tu 1 ngày cuối tuần</h2>
                    <div className={styles["status"]}>
                        <span>Finished</span>
                        <span>E-ticket</span>
                    </div>
                    <p><i class="fas fa-receipt"></i> Order code: 815656587</p>
                    <p><i class="far fa-clock"></i> 08:30 - 16:30, 16 Feb, 2025</p>
                    <p><i class="fas fa-map-marker-alt"></i> Tịnh Viện Pháp Thường, Nhơn Trạch Đồng Nai</p>
                    <p>Tinh Vien Phap Thuong, Huyen Nhon Trach, Dong Nai, Xã Phú Đông, Huyện Nhơn Trạch, Tỉnh Đồng Nai</p>
                </div>
            </div>
        </div>
    );
}

export default MyTicket;