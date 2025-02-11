import React from "react";
import { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import styles from "./EventDetail.module.css"
import { GetEventDetailByIdApi } from "../../../services/api/EventApi";
import ReactQuill from "react-quill";
import 'react-quill/dist/quill.snow.css';
function EventDetail()
{
    const navigate = useNavigate();
    const {eventId} = useParams();
    const [event, setEvent] = useState({});
    const [tickets, setTickets] = useState([]);
    const [minPriceOfTickets, setMinPriceOfTickets] = useState(0);

    useEffect(() => {
        const fetchEvent = async () => {
            const data = await GetEventDetailByIdApi(eventId);
            setEvent(data.event);
            setTickets(data.tickets);
        };
        fetchEvent();
    }, [eventId]);
    useEffect(() => {
        if(tickets.length > 0){
            setMinPriceOfTickets(Math.min(...tickets.map(ticket => ticket.price)));
        }
    }, [tickets]);

    const HandleBookNow = () => {
        navigate(`/booking/${eventId}/select-ticket`);
    }
    return (
        <div className={styles["container"]}>

            <div className={styles["event-header"]}>
                <div className={styles["details"]}>
                    <h1>{event.name}</h1>
                    <p><i class="far fa-calendar-alt"></i>{event.time} - {event.date} </p>
                    <p><i class="fas fa-map-marker-alt"></i> {event.location}</p>
                    <p className={styles["price"]}>From {minPriceOfTickets} đ</p>
                    <button disabled>Online booking closed</button>
                </div>
                <img src={event.bannerPath} alt="Event banner"/>
            </div>

            <div className={styles["content"]}>
                <div className={styles["about"]}>
                    <h2>About</h2>
                    <ReactQuill value={event.description} readOnly theme="bubble"/>
                </div>
                <div className={styles["ticket-info"]}>
                    <h2>Ticket Information</h2>
                    <div className={styles["book-now-section"]}>
                        <div className={styles["time"]}>
                            <i class="fas fa-chevron-down"></i>
                            <span>{event.time} - {event.date}</span>
                        </div>
                        <button onClick={HandleBookNow} className={styles["book-now-button"]}>Book now</button>
                    </div>

                    {tickets.map((ticket) => (
                        <div className={styles["ticket"]}>
                            <div>
                                <p class="text-gray-500">{ticket.name}</p>
                                <p class="font-semibold">{ticket.service}</p>
                            </div>
                            <div className={styles["text-right"]}>
                                <p className={styles["price"]}>{ticket.price} đ</p>
                                <button disabled>Online booking closed</button>
                            </div>
                        </div>
                        ))}
                </div>

                <div className={styles["organizer"]}>
                    <h2>Organizer</h2>
                    <div className={styles["info"]}>
                        <img src="https://storage.googleapis.com/a1aa/image/51cL97iwlvqIZueWm9MlY2WLi_kL3NQ0TXifSDBf8U0.jpg" alt="Organizer logo"/>
                        <div className={styles["text"]}>
                            <p class="font-semibold">SÂN KHẤU NGHỆ THUẬT TRƯƠNG HỒNG MINH</p>
                            <p class="text-gray-500">Nhà hát Biểu Diễn Nghệ Thuật Trương Hồng Minh</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default EventDetail;