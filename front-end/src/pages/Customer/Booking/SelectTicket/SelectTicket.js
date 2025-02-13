import React, { useEffect } from "react";
import styles from "./SelectTicket.module.css";
import { useNavigate, useParams } from "react-router-dom";
import { useState } from "react";
import { GetEventWithTicketsByIdApi } from "../../../../services/api/EventApi";
import { GetUser } from "../../../../services/UserStorageService";
import {format} from 'date-fns';
import { CreateOrderApi } from "../../../../services/api/OrderApi";

function SelectTicket() {
    const navigate = useNavigate();
    const {eventId} = useParams();
    const currentUser = GetUser();

    const [event, setEvent] = useState({});
    const [tickets, setTickets] = useState([]);
    const [order, setOrder] = useState({
        price: 0,
        createdBy: "pqkiet854",
        eventId: eventId,
        dateCreatedAt: format(new Date(), 'yyyy-MM-dd'),
        timeCreatedAt: format(new Date(), 'HH:mm:ss'),
        status: "PENDING",
        promotionId: -1
    });

    useEffect(() => {
        const fetchEvent = async () => {
            const data = await GetEventWithTicketsByIdApi(eventId);
            setEvent(data.event);
            setTickets(data.tickets.map(ticket => ({...ticket, qtyInOrder: 0})));
        }
        fetchEvent();
    }, [eventId]);
    useEffect(() => {
        setOrder((prev) => (
            {...prev, price: tickets.reduce((total, ticket) =>
                 total + ticket.price * ticket.qtyInOrder, 0)}));
    }, [tickets]);

    const HandleTicketQuantityChange = (ticketId, quantity) => {
        setTickets((prev) =>{
            if(prev.some(ticket => ticket.id === ticketId))
            {
                return prev.map((ticket) => ticket.id === ticketId ? {...ticket, qtyInOrder: quantity} : ticket);
            }
            return [...prev, {id: ticketId, qtyInOrder: quantity}];
        })
    };
    const HandleBack = () => {
        window.history.back();
    }
    const HandleContinue = async () => {
        const ticketsOfOrder =  tickets.filter(ticket => ticket.qtyInOrder > 0).map(ticket => ({ticketId: ticket.id, quantity: ticket.qtyInOrder}));
        const createOrderRequest = {
            order: order,
            ticketOfOrders: ticketsOfOrder
        };

        console.log(createOrderRequest);

        const createOrderResponse = await CreateOrderApi(createOrderRequest);
        if(createOrderResponse){
            if(createOrderResponse.message === "success"){
                navigate(`/booking/${eventId}/question-form/${createOrderResponse.orderId}`);
            }
            else{
                alert("Error: " + createOrderResponse.message);
                return;
            }
        }
        else{
            alert("Error: Can not create order");
            return;
        }
    }

    return (
        <div className={styles["container"]}>
        <div className={styles["left-section"]}>
            <div onClick={HandleBack} className={styles["back-button"]}>
                <i class="fas fa-arrow-left"></i>
                <span>Back</span>
            </div>
            <h1>Select Ticket</h1>
            {tickets.map(ticket => ( 
                <div className={styles["ticket-type"]}>
                        <h2>{ticket.name}</h2>
                        <div className={styles["ticket-info"]}>
                            <div className={styles["header"]}>
                                <p>{ticket.price} đ</p>
                            </div>
                            <div className={styles["quantity"]}>
                                    <button
                                        className={styles["select-quantity-button"]}
                                        disabled={ticket.qtyInOrder === 0} 
                                        onClick={() => HandleTicketQuantityChange(ticket.id, ticket.qtyInOrder -1 )}>-</button>
                                    <span>{ticket.qtyInOrder}</span>
                                    <button
                                        className={styles["select-quantity-button"]}
                                        disabled={ticket.qtyInOrder >= ticket.maxQtyInOrder}
                                        onClick={() => HandleTicketQuantityChange(ticket.id, ticket.qtyInOrder + 1)}>+</button>
                            </div>
                        </div>
                    <div className={styles["info"]}>
                        <i class="fas fa-info-circle"></i>
                        <span>{ticket.service}</span>
                    </div>
                </div>
            ))}
        </div>
        <div className={styles["right-section"]}>
            <div>
                <h2>{event.name}</h2>
                <div className={styles["info"]}>
                    <i class="fas fa-calendar-alt"></i>
                    <span>{event.time} - {event.date}</span>
                </div>
                <div className={styles["info"]}>
                    <i class="fas fa-map-marker-alt"></i>
                    <span>{event.location}</span>
                </div>
                <div className={styles["pricing"]}>
                    <h3>Pricing</h3>
                    {tickets.map(ticket => (
                        <div>
                            <span>{ticket.name}</span>
                            <span> {ticket.price} đ</span>
                        </div>
                    ))}
                </div>
            </div>
            <div className={styles["footer"]}>
                <div className={styles["tickets"]}>
                    <i class="fas fa-ticket-alt"></i>
                    <span>x2</span>
                </div>
                <button className={styles["continue-button"]} disabled={order.price ===0} onClick={HandleContinue}>
                    Continue - {order.price} đ
                    <i class="fas fa-arrow-right"></i>
                </button>
            </div>
        </div>
    </div>
    );
}
export default SelectTicket;