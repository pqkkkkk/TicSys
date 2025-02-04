import React from "react";
import "./Events.css";
import { GetEventsApi } from "../../../services/api/EventApi";
import { useEffect, useState } from "react";
import { Button, createTheme, Menu, MenuItem, ThemeProvider } from "@mui/material";
function Events() {
    const [events, setEvents] = useState([]);
    const [anchorEl, setAnchorEl] = React.useState(null);
    const open = Boolean(anchorEl);
    const handleClick = (event) => {
        setAnchorEl(event.currentTarget);
    };
    const handleClose = () => {
        setAnchorEl(null);
    };
    useEffect(() => {
        const FetchEvents = async () => {
            const response = await GetEventsApi();
            console.log(response);
            if(response){
                setEvents(response);
            }
        }
        FetchEvents();
    },[]);
    const darkTheme = createTheme({
        palette: {
          mode: 'dark',
        },
        });
    return (
        <ThemeProvider theme={darkTheme}>
            <main>
                <div className="search-bar">
                    <input type="text" placeholder="Search events"/>
                    <button>Search</button>
                </div>

                <section>
                    <div className="status-filter-bar">
                        <p>Upcoming</p>
                        <p>Previous</p>
                        <p>Cancelled</p>
                    </div>
                    <div className="event-list">
                        {events.map((event) =>(
                            <div className="event-card">
                            <img src={event.bannerPath} alt="Event Image"/>
                            <div className="event-details">
                                <h3 className="event-name">{event.name}</h3>
                                <p className="event-location">Location: {event.location}</p>
                                <p className="event-time">Time: {event.date} - {event.time}</p>
                            </div>
                            <div className="event-actions">
                                <Button
                                    onClick={handleClick}
                                    variant="contained">Actions</Button>
                                <Menu
                                    anchorEl={anchorEl}
                                    open={open}
                                    onClose={handleClose}>
                                    <MenuItem onClick={handleClose}>View Detail</MenuItem>
                                    <MenuItem onClick={handleClose}>Delete</MenuItem>
                                    <MenuItem onClick={handleClose}>Promotion</MenuItem>
                                    <MenuItem onClick={handleClose}>View Report</MenuItem>
                                </Menu>
                            </div>
                        </div>
                        ))}
                    </div>
                </section>
            </main>
    </ThemeProvider>
    );
}
export default Events;