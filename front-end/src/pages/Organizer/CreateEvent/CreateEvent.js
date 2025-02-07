import React from "react";
import EventInfo from "./EventInfo/EventInfo";
import styles from "./CreateEvent.module.css"
import TicketSetUp from "./TicketSetUp/TicketSetUp";
import { useState, useEffect } from "react";
function CreateEvent(){
    const [currentStep,setCurrenrStep] = useState(1);
    const HandleBackClick = () =>{
        setCurrenrStep(currentStep-1);
    }
    const HandleContinueClick = () =>{
        setCurrenrStep(currentStep+1);
    }
    return (
        <div className={styles["create-event"]}>
            <div className={styles["step-container"]}>
                <div className={styles["steps"]}>
                    <div className={styles["step"]}>
                        <div className={currentStep === 1 ? `${styles["circle"]} ${styles["circle-active"]}` : styles["circle"]}>1</div>
                        <span>Event information</span>
                    </div>
                    <div className={styles["step"]}>
                    <div className={currentStep === 2 ? `${styles["circle"]} ${styles["circle-active"]}` : styles["circle"]}>2</div>
                        <span>Ticket</span>
                    </div>
                </div>
                <div className={styles["buttons"]}>
                    {currentStep !== 1 && <button onClick={HandleBackClick} className={styles["continue"]}>Back</button>}
                    {currentStep !== 2 && <button onClick={HandleContinueClick} className={styles["continue"]}>Continue</button>}
                </div>
            </div>
            <div className={styles["create-event-main-content"]}>
                {currentStep === 1 && <EventInfo />}
                {currentStep === 2 && <TicketSetUp/>}
            </div>
        </div>
    );
}

export default CreateEvent;