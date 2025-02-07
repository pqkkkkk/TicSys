import React, { useState } from "react";
import {Box, Button, createTheme, FormControl, Input,
     InputLabel, LinearProgress, MenuItem, Select, TextField, ThemeProvider } from "@mui/material";
import { DatePicker, LocalizationProvider, TimePicker} from '@mui/x-date-pickers';
import { AdapterDateFns } from '@mui/x-date-pickers/AdapterDateFnsV3';
import { format, set } from 'date-fns';
import { CreateEventApi } from "../../../../services/api/EventApi";
import styles from "./EventInfo.module.css";
import ReactQuill from "react-quill";
import 'react-quill/dist/quill.snow.css';
import { create } from "@mui/material/styles/createTransitions";
function EventInfo ()
{

    const [banner, setBanner] = useState(null);
    const [seatMap, setSeatMap] = useState(null);
    const [date, setDate] = useState(null);
    const [time, setTime] = useState(null);
    const [name, setName] = useState(null);
    const [description, setDescription] = useState("");
    const [location, setLocation] = useState(null);
    const [category, setCategory] = useState(null);
    const [creatWaiting, setCreateWaiting] = useState(false);
    const darkTheme = createTheme({
        palette: {
          mode: 'dark',
        },
      });    
    const HandleCreateEvent = async () => {
        if(!banner || !seatMap || !date || !time || !name || !description || location === "" || !category)
        {
            alert("Please fill all fields");
            return;
        }
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

        setCreateWaiting(true);
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
            setCreateWaiting(false);
        }
    }
    return (
        <ThemeProvider theme={darkTheme}>
        <div className={styles["main"]}>
            <div className={styles["input-item"]}>
                <InputLabel className={styles["label"]}>Banner</InputLabel>
                <Input
                    onChange={(e) => setBanner(e.target.files[0])}
                    fullWidth
                    type="file">
                </Input>
            </div>
            <div className={styles["input-item"]}>
                <InputLabel className={styles["label"]}>Seat map</InputLabel>
                <Input fullWidth
                    onChange={(e) => setSeatMap(e.target.files[0])}
                    type="file">
                </Input>
            </div>
            <div className={styles["input-item"]}>
                <InputLabel className={styles["label"]}>Date and time</InputLabel>
                <LocalizationProvider
                    className={styles["date-time-input"]}
                    fullWidth
                    dateAdapter={AdapterDateFns}>
                    <DatePicker onChange={(newDate) => setDate(newDate)}/>
                    <TimePicker onChange={(newTime) => setTime(newTime)}/>
                </LocalizationProvider>
            </div>
            <div className={styles["input-item"]}>
                <InputLabel className={styles["label"]}>Name</InputLabel>
                <TextField
                    onChange={(e) => setName(e.target.value)}
                    fullWidth
                    label="Name" variant="outlined" />
            </div>
           
            <div className={styles["input-item"]}>
                <InputLabel className={styles["label"]}>Location</InputLabel>
                <TextField
                    onChange={(e) => setLocation(e.target.value)}
                    fullWidth
                    label="Location" variant="outlined" />
            </div>
            <div className={styles["input-item"]}>
                <InputLabel className={styles["label"]}>Category</InputLabel>
                <FormControl fullWidth>
                    <InputLabel className={styles["label"]}>Category</InputLabel>
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
            <div className={styles["input-item"]}>
                <InputLabel className={styles["label"]}>Description</InputLabel>
                <ReactQuill value={description} onChange={(value) => setDescription(value)}/>
            </div>
            {creatWaiting && <Box sx={{ width: '100%' }}>
                <LinearProgress/>
            </Box>}
            <Button
                onClick={HandleCreateEvent} 
                className={styles["input-item"]}
                variant="contained">Create</Button>

        </div>
        </ThemeProvider>
    );
}

export default EventInfo;