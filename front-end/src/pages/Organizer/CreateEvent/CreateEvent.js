import React, { useState } from "react";
import {Button, createTheme, FormControl, Input, InputLabel, MenuItem, Select, TextField, ThemeProvider } from "@mui/material";
import { DatePicker, LocalizationProvider, TimePicker} from '@mui/x-date-pickers';
import { AdapterDateFns } from '@mui/x-date-pickers/AdapterDateFnsV3';
import { format } from 'date-fns';
import { CreateEventApi } from "../../../services/api/EventApi";
import "./CreateEvent.css";
function CreateEvent ()
{
    const [banner, setBanner] = useState(null);
    const [seatMap, setSeatMap] = useState(null);
    const [date, setDate] = useState(null);
    const [time, setTime] = useState(null);
    const [name, setName] = useState(null);
    const [description, setDescription] = useState(null);
    const [location, setLocation] = useState(null);
    const [category, setCategory] = useState(null);

    const darkTheme = createTheme({
        palette: {
          mode: 'dark',
        },
      });
    const HandleCreateEvent = async () => {
        const formData = new FormData();
        formData.append("organizerId", "admin");
        formData.append("status","Upcoming");
        formData.append("banner", banner);
        formData.append("seatMap", seatMap);
        formData.append("date", format(date, "yyyy-MM-dd"));
        formData.append("time", format(time, "HH:mm:ss"));
        formData.append("name", name);
        formData.append("description", description);
        formData.append("location", location);
        formData.append("category", category);
        const response = await CreateEventApi(formData);
        if(response){
            console.log(response);
            if(response.message === "success")
            {
                alert("Event created successfully");
            }
            else
            {
                alert("Event creation failed");
            }
        }
    }
    return (
        <ThemeProvider theme={darkTheme}>
        <div id="main">
            <div className="input-item">
                <InputLabel className="label">Banner</InputLabel>
                <Input
                    onChange={(e) => setBanner(e.target.files[0])}
                    fullWidth
                    type="file">
                </Input>
            </div>
            <div className="input-item">
                <InputLabel className="label">Seat map</InputLabel>
                <Input fullWidth
                    onChange={(e) => setSeatMap(e.target.files[0])}
                    type="file">
                </Input>
            </div>
            <div className="input-item">
                <InputLabel className="label">Date and time</InputLabel>
                <LocalizationProvider
                    className="date-time-input"
                    fullWidth
                    dateAdapter={AdapterDateFns}>
                    <DatePicker onChange={(newDate) => setDate(newDate)}/>
                    <TimePicker onChange={(newTime) => setTime(newTime)}/>
                </LocalizationProvider>
            </div>
            <div className="input-item">
                <InputLabel className="label">Name</InputLabel>
                <TextField
                    onChange={(e) => setName(e.target.value)}
                    fullWidth
                    label="Name" variant="outlined" />
            </div>
            <div className="input-item">
                <InputLabel className="label">Description</InputLabel>
                <TextField
                    onChange={(e) => setDescription(e.target.value)}
                    fullWidth
                    label="Description" variant="outlined" />
            </div>
            <div className="input-item">
                <InputLabel className="label">Location</InputLabel>
                <TextField
                    onChange={(e) => setLocation(e.target.value)}
                    fullWidth
                    label="Location" variant="outlined" />
            </div>
            <div className="input-item">
                <InputLabel className="label">Category</InputLabel>
                <FormControl fullWidth>
                    <InputLabel className="label">Category</InputLabel>
                    <Select
                        onChange={(e) => setCategory(e.target.value)}
                        label="Category">
                        <MenuItem value={"Sport"}>Sport</MenuItem>
                        <MenuItem value={"Music"}>Music</MenuItem>
                        <MenuItem value={"Comedy"}>Comedy</MenuItem>
                        <MenuItem value={"Other"}>Other</MenuItem>
                    </Select>
                </FormControl>
            </div>

            <Button
                onClick={HandleCreateEvent} 
                className="input-item"
                variant="contained">Create</Button>
        </div>
        </ThemeProvider>
    );
}

export default CreateEvent;