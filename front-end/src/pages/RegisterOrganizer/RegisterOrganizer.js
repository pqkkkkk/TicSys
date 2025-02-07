import { ThemeProvider } from "@emotion/react";
import { createTheme, Input, InputLabel, TextField } from "@mui/material";
import { Container, Box, Typography, Button } from "@mui/material";
import React from "react";
import styles from "./RegisterOrganizer.module..css";
function RegisterOrganizer() {
    const darkTheme = createTheme({
        palette: {
          mode: "dark",
        },  
    });
    return (
        <ThemeProvider theme={darkTheme}>
            <Container className={styles["register-organizer-page"]}>
                <Box className={styles["register-organizer"]}>
                    <Box className={styles["register-organizer-title"]}>
                        <Typography align="center" marginBottom={5} variant="h4">Become Organizer</Typography>
                    </Box>
                    <Box className={styles["register-organizer-form"]}>
                        <Box className={styles["register-organizer-input-item"]}>
                            <InputLabel className={styles["register-organizer-label"]} htmlFor="organizername">Organizer name</InputLabel>
                            <TextField
                                className={styles["register-organizer-input"]} id="organizername" label="Organizer name" variant="outlined" fullWidth/>
                        </Box>
                        <Box className={styles["register-organizer-input-item"]}>
                            <InputLabel className={styles["register-organizer-label"]} htmlFor="organizerdescription">Brief Description</InputLabel>
                            <TextField
                                className={styles["register-organizer-input"]} id="organizerdescription" label="Brief description" variant="outlined" fullWidth/>
                        </Box>
                        <Box className={styles["register-organizer-input-item"]}>
                            <InputLabel className={styles["register-organizer-label"]} htmlFor="organizeravt">Organizer Logo</InputLabel>
                            <Input
                                type="file"
                                className={styles["register-organizer-input"]} id="organizeravt" variant="outlined" fullWidth/>
                        </Box>
                        <Box className={styles["register-organizer-input-item"]}>
                            <Button variant="contained" color="primary" fullWidth>Submit</Button>
                        </Box>
                    </Box>      
                </Box>
            </Container>
        </ThemeProvider>
    );
}

export default RegisterOrganizer;