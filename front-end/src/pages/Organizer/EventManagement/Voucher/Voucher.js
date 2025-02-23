import React from "react";
import styles from "./Voucher.module.css";

function Voucher() {
    return (
        <div className={styles["container"]}>
            <div className={styles["search-bar"]}>
                <div className={styles["search-input"]}>
                    <input type="text" placeholder="Tìm kiếm theo tên chương trình và mã voucher"/>
                    <button><i className="fas fa-search"></i></button>
                </div>
                <button className={styles["create-voucher"]}>Tạo voucher</button>
            </div>
            <div className={styles["table-container"]}>
                <table>
                    <thead>
                        <tr>
                            <th>Tên chương trình khuyến mãi</th>
                            <th>Mã voucher</th>
                            <th>Mức giảm</th>
                            <th>Thời gian áp dụng</th>
                            <th>Trạng thái hoạt động <i className="fas fa-info-circle"></i></th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td colSpan="6" className={styles["no-data"]}>
                                <img src="https://storage.googleapis.com/a1aa/image/1YpG5oAyAXLuohgYlFY4m-cI-D0PbB56-YdnEu8l248.jpg" alt="No data icon" width="50" height="50"/>
                                No data
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
    );
}

export default Voucher;