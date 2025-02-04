import { ThemeProvider } from "@emotion/react";
import { createTheme, Input, InputLabel, TextField } from "@mui/material";
import { Container, Box, Typography, Button } from "@mui/material";
import React from "react";
import "./RegisterOrganizer.css";
function RegisterOrganizer() {
    const darkTheme = createTheme({
        palette: {
          mode: "dark",
        },  
    });
    return (
        <ThemeProvider theme={darkTheme}>
            <Container className="register-organizer-page">
                <Box className="register-organizer">
                    <Box className="register-organizer-title">
                        <Typography align="center" marginBottom={5} variant="h4">Become Organizer</Typography>
                    </Box>
                    <Box className="register-organizer-form">
                        <Box className="register-organizer-input-item">
                            <InputLabel className="register-organizer-label" htmlFor="organizername">Organizer name</InputLabel>
                            <TextField
                                className="register-organizer-input" id="organizername" label="Organizer name" variant="outlined" fullWidth/>
                        </Box>
                        <Box className="register-organizer-input-item">
                            <InputLabel className="register-organizer-label" htmlFor="organizerdescription">Brief Description</InputLabel>
                            <TextField
                                className="register-organizer-input" id="organizerdescription" label="Brief description" variant="outlined" fullWidth/>
                        </Box>
                        <Box className="register-organizer-input-item">
                            <InputLabel className="register-organizer-label" htmlFor="organizeravt">Organizer Logo</InputLabel>
                            <Input
                                type="file"
                                className="register-organizer-input" id="organizeravt" variant="outlined" fullWidth/>
                        </Box>
                        <Box className="register-organizer-input-item">
                            <Button variant="contained" color="primary" fullWidth>Submit</Button>
                        </Box>
                    </Box>      
                </Box>
            </Container>
        </ThemeProvider>
    );
}

export default RegisterOrganizer;