import React from "react";
import styles from "./TicketSetUp.module.css"
import { useState } from "react";
function TicketSetUp(){
    const [isAddTicketModalOpen, setIsAddTicketModalOpen] = useState(false);
    const HandleCloseAddTicketModal = () =>{
        setIsAddTicketModalOpen(false);
    }
    const HandleOpenAddTicketModal = () =>{
        setIsAddTicketModalOpen(true);
    }
    return(
        <div className={styles["ticket-setup-container"]}>
            <div className={styles["ticket-list"]}>
                <div className={styles["ticket-item"]}>
                    <i class="fas fa-bars"></i>
                    <i class="fas fa-ticket-alt"></i>
                    <span>VIP</span>
                    <button><i class="fas fa-pen"></i></button>
                    <button class="delete"><i class="fas fa-trash"></i></button>
                </div>
            </div>
            <div className={styles["add-ticket"]}>
                <i class="fas fa-plus-circle" onClick={HandleOpenAddTicketModal}></i> Add another ticket type
            </div>

            {isAddTicketModalOpen && 
                <div className={styles["overlay"]}>
                    <div className={styles["add-ticket-modal"]}>
                        <div className={styles["header"]}>
                            <h2>Add another ticket type</h2>
                            <button onClick={HandleCloseAddTicketModal}>&times;</button>
                        </div>
                        <form>
                            <div className={styles["form-group"]}>
                                <label for="ticket-name">* Ticket name</label>
                                <div className={styles["input-group"]}>
                                    <input type="text" id="ticket-name" value="VIP"/>
                                    <span>3 / 50</span>
                                </div>
                            </div>
                            <div className={styles["form-group"]}>
                                <div clasName={styles["form-group"]}>
                                    <label for="price">* Price</label>
                                    <input type="text" id="price" value="500.000"/>
                                </div>
                                <div className={styles["form-group"]}>
                                    <label for="total-tickets">* Total number of ticket</label>
                                    <input type="text" id="total-tickets" value="10"/>
                                </div>
                                <div className={styles["form-group"]}>
                                    <label for="min-tickets">* Minimum number of tickets for one purchase</label>
                                    <input type="text" id="min-tickets" value="1"/>
                                </div>
                                <div className={styles["form-group"]}>
                                    <label for="max-tickets">* Maximum number of tickets for one purchase</label>
                                    <input type="text" id="max-tickets" value="10"/>
                                </div>
                            </div>
                            <div className={styles["form-group"]}>
                                <label for="description">Ticket description</label>
                                <textarea id="description" rows="4" placeholder="Description"></textarea>
                                <div className={styles["text-right text-gray-400 text-sm"]}>0 / 1000</div>
                            </div>
                            <div className={styles["text-center"]}>
                                <button type="submit" className={styles["btn"]}>Save</button>
                            </div>
                        </form>
                </div>
                </div>}
        </div>
    );
}

export default TicketSetUp;