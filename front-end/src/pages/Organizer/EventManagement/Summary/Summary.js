import React from "react";
import styles from "./Summary.module.css";

function Summary(){
    return (
        <div className={styles["summary-container"]}>
            <div className={styles["header"]}>
                <div>
                    <p className={styles["amount"]}>0 VND</p>
                    <p>Giao dịch thành công</p>
                </div>
                <div>
                    <p className={styles["amount"]}>0 VND</p>
                    <p>Giao dịch lỗi</p>
                </div>
                <div>
                    <p className={styles["amount"]}>0 VND</p>
                    <p>Tổng</p>
                </div>
            </div>
            <div className={styles["main-content"]}>
                <h2>Summary</h2>
                <div className={styles["date-picker"]}>
                    <div className={styles["relative"]}>
                        <input type="text" placeholder="16-02-2025"/>
                        <i class="fas fa-calendar-alt"></i>
                    </div>
                    <span>Đến</span>
                    <div className={styles["relative"]}>
                        <input type="text" placeholder="23-02-2025"/>
                        <i class="fas fa-calendar-alt"></i>
                    </div>
                </div>
                <div className={styles["charts"]}>
                    <div className={styles["chart"]}>
                        <h3>Tổng tiền giao dịch</h3>
                        <img src="https://placehold.co/600x200" alt="Line chart showing transaction amounts over time"/>
                        <p>Tổng: 0 VND</p>
                    </div>
                    <div className={styles["chart"]}>
                        <h3>Giao dịch thành công</h3>
                        <img src="https://placehold.co/300x200" alt="Line chart showing successful transactions over time"/>
                        <p>Tổng: 0 VND</p>
                    </div>
                    <div className={styles["chart"]}>
                        <h3>Giao dịch đang thực hiện</h3>
                        <img src="https://placehold.co/300x200" alt="Line chart showing ongoing transactions over time"/>
                        <p>Tổng: 0 VND</p>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Summary;