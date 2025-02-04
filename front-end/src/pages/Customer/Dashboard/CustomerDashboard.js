import React from "react";
import { useState,useEffect } from "react";
import { GetEventsApi } from "../../../services/api/EventApi";
import "./CustomerDashboard.css";
import ArtificialParadise from "../../../assets/image/ArtificialParadise.jpg";
import RoadTripTo1900 from "../../../assets/image/RoadTripTo1900.png";
function CustomerDashboard() {
  const [events, setEvents] = useState([]);
  useEffect(() => {
    const fetchEvents = async () => {
      const data = await GetEventsApi();
      setEvents(data);
    };
    fetchEvents();
  }, []);
  return (
    <div>
    <div className="type-bar">
        <nav>
            <a href="#">Music</a>
            <a href="#">Theater & Art</a>
            <a href="#">Sport</a>
            <a href="#">Others</a>
        </nav>
    </div>

    <div className="hero">
        <img src={ArtificialParadise} alt="Event 1"/>
        <img src={RoadTripTo1900} alt="Event 2"/>
    </div>

    <section className="top-picks-section">
        <h2>Top Picks for You</h2>
        <div className="top-picks">
          {events.map((event) =>(
            <div className="dashboard-event-card">
                <img src={event.bannerPath} alt="Event 1"/>
                <p className="event-name">{event.name}</p>
                <p className="event-time">{event.date}  {event.time}</p>
                <p className="event-price">From 300,000₫</p>
            </div>
          ))}
            
        </div>
    </section>
</div>
  );
}
export default CustomerDashboard;