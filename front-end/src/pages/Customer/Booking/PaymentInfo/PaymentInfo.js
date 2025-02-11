import React from "react";
import styles from "./PaymentInfo.module.css";
import vnPayLogo from "../../../../assets/image/vnpay.jpg";
import zaloPayLogo from "../../../../assets/image/zalopaylogo.png";
import shopeePayLogo from "../../../../assets/image/shopeepaylogo.png";
import vietQRLogo from "../../../../assets/image/vietQRLogo.png";
import { useParams, useNavigate } from "react-router-dom";
import { useState, useEffect } from "react";
import { GetEventByIdApi } from "../../../../services/api/EventApi";
import { GetDetailOrderByIdApi, ReverseOrderApi } from "../../../../services/api/OrderApi";
import { GetUser } from "../../../../services/UserStorageService";
function PaymentInfo() {
    const navigate = useNavigate();
    const {eventId, orderId} = useParams();
    const currentUser = GetUser();
    
    const [event, setEvent] = useState({});
    const [order, setOrder] = useState({});
    const [ticketOfOrders, setTicketOfOrders] = useState([]);
    const [ticketInfos, setTicketInfos] = useState([]);
    const [totalTickets, setTotalTickets] = useState(0);

    useEffect(() => {
        const fetchEvent = async () => {
            const [eventData, orderData] = await Promise.all([GetEventByIdApi(eventId),
                                                            GetDetailOrderByIdApi(orderId)]);
            if(orderData.order.status === "PAID")
            {
                navigate("/error");
            }
            setOrder(orderData.order);
            setTicketOfOrders(orderData.ticketOfOrders);
            setTicketInfos(orderData.ticketInfos);
            setEvent(eventData);
        }
        fetchEvent();
    }, [eventId]);
    useEffect(() => {
        setTotalTickets(ticketOfOrders.reduce((total, ticketOfOrder) => total + ticketOfOrder.quantity, 0));
    }, [ticketOfOrders]);

    const HandlePayment = async () => {
        const response = await ReverseOrderApi(orderId);
        console.log(response);
        if(response)
        {
            if(response === "success"){
                alert("Payment success");
                navigate("/");
            }
        }
        else{
            alert("Payment failed");
        }
    }
    return(
        <div className={styles["container"]}>
             <div className={styles["step-container"]}>
                <div className={styles["step"]}>
                    <div className={styles["circle"] + " " + styles["completed"]}>
                        <i class="fas fa-check"></i>
                    </div>
                    <div className={styles["text"] + " " + styles["completed"]}>Select Ticket</div>
                </div>
                <div className={styles["divider"]}></div>
                <div className={styles["step"]}>
                    <div className={styles["circle"] + " " + styles["completed"]}>
                        <i class="fas fa-check"></i>
                    </div>
                    <div className={styles["text"] + " " + styles["completed"]}>Question Form</div>
                </div>
                <div className={styles["divider"]}></div>
                <div className={styles["step"]}>
                    <div className={styles["circle"] + " " + styles["active"]}>
                        <div className={styles["inner-circle"]}></div>
                    </div>
                    <div className={styles["text"] + " " + styles["active"]}>Payment Info</div>
                </div>
            </div>

            <div className={styles["event-info-and-timer"]}>
                <div className={styles["event-info"]}>
                    <h1>{event.name}</h1>
                    <div className={styles["details"]}>
                        <div><i class="fas fa-map-marker-alt"></i>{event.location} </div>
                        <div><i class="fas fa-calendar-alt"></i>{event.time} - {event.date}</div>
                    </div>
                </div>
                <div className={styles["timer"]}>
                    <div className={styles["box"]}>
                        <div className={styles["text"]}>Complete your booking within</div>
                        <div className={styles["time"]}>14 : 35</div>
                    </div>
                </div>
            </div>
            <div className={styles["main-content"]}>
                <div className={styles["payment-info"]}>
                    <h2>PAYMENT INFO</h2>
                    <div className={styles["alert"]}>
                        <i class="fas fa-info-circle"></i>
                        <span>Please check ticket receiving info. If there are any changes, <a href="#">please update here</a></span>
                    </div>
                    <div className={styles["info-box"]}>
                        <div>
                            <div className={styles["font-semibold"]}>Ticket receiving info</div>
                            <div>{currentUser ? currentUser.fullName : "No name"}</div>
                            <div>{currentUser ? currentUser.phoneNumber : "No phone number"}</div>
                            <div>{currentUser ? currentUser.email : "No email"}</div>
                        </div>
                        <a href="#">Edit</a>
                    </div>
                    <div className={styles["payment-method"]}>
                        <div className={styles["font-semibold"]}>Payment method</div>
                        <div className={styles["method"]}>
                            <input type="radio" id="vnpay" name="payment"/>
                            <label htmlFor="vnpay">
                                <img src={vnPayLogo} alt="VNPAY logo" width="24" height="24"/>
                                VNPAY
                                <span>New</span>
                            </label>
                        </div>
                        <div className={styles["method"]}>
                            <input type="radio" id="zalopay" name="payment"/>
                            <label htmlFor="zalopay">
                                <img src={zaloPayLogo} alt="Zalopay logo" width="24" height="24"/>
                                Zalopay
                                <span>Coupon</span>
                            </label>
                            <a href="#">Details</a>
                        </div>
                        <div className={styles["method"]}>
                            <input type="radio" id="shopeepay" name="payment"/>
                            <label htmlFor="shopeepay">
                                <img src={shopeePayLogo} alt="ShopeePay logo" width="24" height="24"/>
                                ShopeePay Wallet
                                <span>Coupon</span>
                            </label>
                            <a href="#">Details</a>
                        </div>
                        <div className={styles["method"]}>
                            <input type="radio" id="vietqr" name="payment"/>
                            <label htmlFor="vietqr">
                                <img src={vietQRLogo} alt="VietQR logo" width="24" height="24"/>
                                VietQR
                            </label>
                        </div>
                    </div>
                </div>
                <div className={styles["order-summary"]}>
                    <div className={styles["ticket-infos"]}>
                        <div className={styles["header"]}>
                            <div className={styles["font-semibold"]}>Ticket information</div>
                            <a href="#">Reselect Ticket</a>
                        </div>
                        <div className={styles["details"]}>
                            <div className={styles["title"]}>
                                <span>Ticket type</span>
                                <span>Quantity</span>
                            </div>
                            {ticketOfOrders.map((ticketOfOrder, index) => (
                                <div className={styles["ticket-info-container"]}>
                                    <div className={styles["ticket-info"]}>
                                        <span>{ticketInfos.at(index).name}</span>
                                        <span>{ticketOfOrder.quantity}</span>
                                    </div>
                                    <div className={styles["ticket-info"]}>
                                        <span>{ticketInfos.at(index).price} đ</span>
                                        <span>{ticketInfos.at(index).price * ticketOfOrder.quantity} đ</span>
                                    </div>
                                    <div className={styles["ticket-info-divider"]}></div>
                                </div>
                                ))}
                        </div>
                    </div>
                    <div className={styles["order-info"]}>
                        <div className={styles["header"]}>
                            <div className={styles["font-semibold"]}>Discount</div>
                        </div>
                        <div className={styles["discount"]}>
                            <input type="text" placeholder="ENTER DISCOUNT CODE"/>
                            <button>Apply</button>
                        </div>
                        <div className={styles["details"]}>
                            <div>
                                <span>Subtotal</span>
                                <span>{order.price} đ</span>
                            </div>
                            <div>
                                <span>Total</span>
                                <span className={styles["total"]}>{order.price} đ</span>
                            </div>
                        </div>
                        <div className={styles["terms"]}>
                            By proceeding the order, you agree to the <a href="#">General Trading Conditions</a>
                        </div>
                        <button onClick={HandlePayment} className={styles["payment-btn"]}>Payment</button>
                    </div>
                </div>
            </div>
    </div>
)};

export default PaymentInfo;