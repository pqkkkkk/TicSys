import React from "react";
import styles from "./CreateVoucher.module.css";

function CreateVoucher(){
    return (
        <div className={styles["create-voucher-container"]}>
            <div className={styles["header"]}>
                <button><i class="fas fa-arrow-left"></i></button>
                <h1>Tạo voucher mới</h1>
            </div>
            <div className={styles["grid"]}>
                <div className={styles["card"]}>
                    <h2>Tạo 1 mã</h2>
                    <p>Tạo một mã voucher duy nhất cho sự kiện của bạn</p>
                    <button>Đã chọn</button>
                </div>
                <div className={styles["card"]}>
                    <h2>Tạo nhiều mã</h2>
                    <p>Mã voucher sẽ được tạo ngẫu nhiên hàng loạt và các thiết lập bên dưới sẽ áp dụng cho tất cả các mã voucher</p>
                    <button>Chọn</button>
                </div>
            </div>
            <div className={styles["section"]}>
                <h2>Thông tin cơ bản</h2>
                <label>Tên chương trình khuyến mãi:</label>
                <input type="text" placeholder="Nhập tên chương trình khuyến mãi"/>
                <p>Tên chương trình sẽ hiển thị cho người mua</p>
                <label>Mã voucher:</label>
                <input type="text" placeholder="Nhập mã voucher"/>
                <p>Chỉ cho phép những giá trị sau (A-Z and 0-9), tối thiểu 6 và tối đa 12 ký tự</p>
                <label>Thời gian sử dụng mã:</label>
                <div className={styles["flex"]}>
                    <input type="text" placeholder="Start date"/>
                    <input type="text" placeholder="End date"/>
                </div>
            </div>
            <div className={styles["section"]}>
                <h2>Thiết lập mã voucher</h2>
                <label>Loại khuyến mãi:</label>
                <div className={styles["flex"]}>
                    <select>
                        <option>Theo số tiền</option>
                    </select>
                    <input type="text" placeholder="Nhập mức giảm"/>
                </div>
                <p>Số vé được khuyến mãi mỗi voucher</p>
                <label>Số đơn hàng tối đa/Người mua:</label>
                <input type="text" placeholder="Nhập số đơn hàng tối đa"/>
                <p>Tổng số đơn hàng mà người mua có thể áp dụng voucher</p>
                <label>Số lượng vé tối thiểu:</label>
                <input type="text" placeholder="Nhập số lượng vé tối thiểu"/>
                <p>Số lượng vé tối thiểu trong đơn hàng để có thể áp dụng mã voucher</p>
                <label>Số lượng vé tối đa:</label>
                <input type="text" placeholder="Nhập số lượng vé tối đa"/>
                <p>Số lượng vé tối đa trong đơn hàng có thể áp dụng mã voucher</p>
            </div>
            <div className={styles["footer"]}>
                <button className={styles["cancel"]}>Hủy</button>
                <button className={styles["create"]}>Tạo voucher</button>
            </div>
        </div>
    );
}

export default CreateVoucher;