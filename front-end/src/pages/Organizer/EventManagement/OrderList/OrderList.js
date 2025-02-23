import React from "react";
import styles from "./OrderList.module.css";

function OrderList(){
    return(
        <div className={styles["order-list-container"]}>
        <div className={styles["search-input"]}>
            <input type="text" placeholder="Tìm kiếm theo tên"/>
            <button><i className="fas fa-search"></i></button>
        </div>
        <div className={styles["header"]}>
            <input type="checkbox"/>
            <div className={styles["order-title"]}>9 ORDERs</div>
            <div className={styles["quantity-title"]}>QTY</div>
            <div className={styles["total-title"]}>TOTAL 0</div>
        </div>
        <ul className={styles["order-list"]}>
            <li className={styles["order-item"]}>
                <input type="checkbox"/>
                <div className={styles["order-info"]}>
                    <i class="fas fa-check-circle"></i>
                    <div className={styles["details"]}>
                        <div className={styles["name"]}>QUỐC ĐÔ TRẦN</div>
                        <div className={styles["email"]}>t*****9@gmail.com</div>
                        <div className={styles["phone"]}>09*****510</div>
                        <div className={styles["date"]}>Đặt vào lúc <span>Thu, 20 Oct 2022 7:05 PM</span></div>
                    </div>
                </div>
                <div className={styles["quantity"]}>2</div>
                <div className={styles["price"]}>180,000</div>
            </li>
            <li className={styles["order-item"]}>
                <input type="checkbox"/>
                <div className={styles["order-info"]}>
                    <i class="fas fa-check-circle"></i>
                    <div className={styles["details"]}>
                        <div className={styles["name"]}>nguyen ivan</div>
                        <div className={styles["email"]}>i****03@gmail.com</div>
                        <div className={styles["phone"]}>09*****560</div>
                        <div className={styles["date"]}>Đặt vào lúc <span>Thu, 20 Oct 2022 10:46 AM</span></div>
                    </div>
                </div>
                <div className={styles["quantity"]}>2</div>
                <div className={styles["price"]}>180,000</div>
            </li>
            <li className={styles["order-item"]}>
                <input type="checkbox"/>
                <div className={styles["order-info"]}>
                    <i class="fas fa-check-circle"></i>
                    <div className={styles["details"]}>
                        <div className={styles["name"]}>Luyện Khánh</div>
                        <div className={styles["email"]}>g*****n@gmail.com</div>
                        <div className={styles["phone"]}>09*****123</div>
                        <div className={styles["date"]}>Đặt vào lúc <span>Wed, 19 Oct 2022 1:59 PM</span></div>
                    </div>
                </div>
                <div className={styles["quantity"]}>3</div>
                <div className={styles["price"]}>270,000</div>
            </li>
            <li className={styles["order-item"]}>
                <input type="checkbox"/>
                <div className={styles["order-info"]}>
                    <i class="fas fa-check-circle"></i>
                    <div className={styles["details"]}>
                        <div className={styles["name"]}>Thi Thu Uyên Nguyễn</div>
                        <div className={styles["email"]}>u****43@gmail.com</div>
                        <div className={styles["phone"]}>03*****886</div>
                        <div className={styles["date"]}>Đặt vào lúc <span>Tue, 18 Oct 2022 11:16 PM</span></div>
                    </div>
                </div>
                <div className={styles["quantity"]}>2</div>
                <div className={styles["price"]}>180,000</div>
            </li>
        </ul>
    </div>
    );
}

export default OrderList;