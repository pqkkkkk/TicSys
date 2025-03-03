import React from "react";
import styles from "./Voucher.module.css";
import {useNavigate, useParams} from "react-router-dom";
import { useState, useEffect } from "react";
import { GetPromotionsOfEvent } from "../../../../services/api/PromotionApi";

function Voucher() {
    const navigate = useNavigate();
    const {eventId} = useParams();

    const [promotions, setPromotions] = useState([]);

    useEffect(() => {
        const fetchPromotions = async () => {
            try {
                const data = await GetPromotionsOfEvent(eventId);
                setPromotions(data);
            } catch (err) {
                console.error(err);
            }
        };
        fetchPromotions();
    }, [eventId]);

    const HandleNavigateToCreateVoucher = () => {
        navigate(`/organizer/myevents/${eventId}/create-voucher`);
    };
    const HandleNavigateToEditVoucher = (promotionId) => {
        navigate(`/organizer/myevents/${eventId}/edit-voucher/${promotionId}`);
    }
    return (
        <div className={styles["container"]}>
            <div className={styles["search-bar"]}>
                <div className={styles["search-input"]}>
                    <input type="text" placeholder="Tìm kiếm theo tên chương trình và mã voucher"/>
                    <button><i className="fas fa-search"></i></button>
                </div>
                <button
                    onClick={HandleNavigateToCreateVoucher}  
                    className={styles["create-voucher"]}>Tạo voucher</button>
            </div>
            <div className={styles["table-container"]}>
                <table>
                    <thead>
                        <tr>
                            <th>Promotion Type</th>
                            <th>Reduced Rate (%)</th>
                            <th>Voucher value</th>
                            <th>Minimum price to apply</th>
                            <th>Duration time</th>
                            <th>Status<i class="fas fa-info-circle"></i></th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        {promotions.map((promotion) => (
                        <tr>
                            <td>
                                <div className={styles["flex"]}>
                                    <i class="fas fa-plus-circle"></i>
                                    <div className={styles["ml-4"]}>{promotion.type}</div>
                                </div>
                            </td>
                            <td>{promotion.promoPercent}</td>
                            <td>{promotion.voucherValue.toLocaleString('vi-VN')} đ</td>
                            <td>{promotion.minPriceToReach.toLocaleString('vi-VN')} đ</td>
                            <td>
                                <div>
                                    <span className={styles["bg-green-100"]}>In progress</span>
                                    <div>{promotion.startDate} to {promotion.endDate}</div>
                                </div>
                            </td>
                            <td>
                                <input type="checkbox" className={styles["toggle-checkbox"]} checked/>
                            </td>
                            <td>
                                <div className={styles["action-buttons"]}>
                                    <button
                                        onClick={() => HandleNavigateToEditVoucher(promotion.id)} 
                                        className={styles["text-green-600"]}><i class="fas fa-edit"></i></button>
                                    <button className={styles["text-green-600"]}><i class="fas fa-sync-alt"></i></button>
                                    <button className={styles["text-green-600"]}><i class="fas fa-trash"></i></button>
                                </div>
                            </td>
                        </tr>
                        ))}
                    </tbody>
                </table>
            </div>
        </div>
    );
}

export default Voucher;