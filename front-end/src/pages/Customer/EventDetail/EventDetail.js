import React from "react";
import { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import styles from "./EventDetail.module.css"
import { GetEventByIdApi } from "../../../services/api/EventApi";
function EventDetail()
{
    const {eventId} = useParams();
    const [event, setEvent] = useState({});
    useEffect(() => {
        const fetchEvent = async () => {
            const data = await GetEventByIdApi(eventId);
            setEvent(data);
        };
        fetchEvent();
    }, [eventId]);

    return (
        <div className={styles["container"]}>

            <div className={styles["event-header"]}>
                <div className={styles["details"]}>
                    <h1>SKNT TRUONG HONG MINH: CHẤN ĐỘNG TAM GIỚI</h1>
                    <p><i class="far fa-calendar-alt"></i> 15:30 - 18:30, 07 FEB, 2025</p>
                    <p><i class="fas fa-map-marker-alt"></i> SÂN KHẤU NGHỆ THUẬT TRƯƠNG HỒNG MINH, 25 Võ Văn Tần, phường 3, quận 10, TP. Hồ Chí Minh</p>
                    <p className={styles["price"]}>From 300.000 đ</p>
                    <button disabled>Online booking closed</button>
                </div>
                <img src={event.bannerPath} alt="Event banner with various characters and the title 'Chấn Động Tam Giới'"/>
            </div>

            <div className={styles["content"]}>
                <div className={styles["about"]}>
                    <h2>About</h2>
                    <h3>Giới thiệu sự kiện:</h3>
                    <p><strong>"CHẤN ĐỘNG TAM GIỚI"</strong></p>
                    <p>Là một tác phẩm đặc biệt của Liên Sơn. Tác ngôn mà Liên Sơn, đặc biệt 30 ngày diễn chuẩn bị luyện tập kỹ lưỡng, với mục tiêu chính là mang đến cho khán giả một trải nghiệm hoàn toàn mới lạ. Tác phẩm là sự kết hợp giữa nghệ thuật truyền thống và hiện đại, với sự tham gia của nhiều nghệ sĩ nổi tiếng.</p>
                    <p>Đạo diễn: Liên Sơn</p>
                    <p>Diễn viên: NSƯT Hồng Vân, Việt Hương, Lê Giang, Bảo Bảo, Thạch Thảo, Mãka, Duy Khương, Song Duy, Dương Lâm, Minh Nhí, Minh Nguyên, Quách Thành Nhân, Thành Thuận, Võ Minh Hải cùng các diễn viên khác.</p>
                    <p>Đơn vị tổ chức: Sân Khấu Nghệ Thuật Trương Hồng Minh</p>
                </div>
                <div className={styles["ticket-info"]}>
                    <h2>Ticket Information</h2>
                    <div className={styles["ticket"]}>
                        <div>
                            <p class="font-semibold">15:30 - 18:30, 07 FEB, 2025</p>
                            <p class="text-gray-500">VIP</p>
                        </div>
                        <div className={styles["text-right"]}>
                            <p className={styles["price"]}>500.000 đ</p>
                            <button disabled>Online booking closed</button>
                        </div>
                    </div>
                    <div className={styles["ticket"]}>
                        <div>
                            <p class="font-semibold">15:30 - 18:30, 07 FEB, 2025</p>
                            <p class="text-gray-500">Thường</p>
                        </div>
                        <div className={styles["text-right"]}>
                            <p className={styles["price"]}>300.000 đ</p>
                            <button disabled>Online booking closed</button>
                        </div>
                    </div>
                </div>

                <div className={styles["organizer"]}>
                    <h2>Organizer</h2>
                    <div className={styles["info"]}>
                        <img src="https://storage.googleapis.com/a1aa/image/51cL97iwlvqIZueWm9MlY2WLi_kL3NQ0TXifSDBf8U0.jpg" alt="Organizer logo"/>
                        <div className={styles["text"]}>
                            <p class="font-semibold">SÂN KHẤU NGHỆ THUẬT TRƯƠNG HỒNG MINH</p>
                            <p class="text-gray-500">Nhà hát Biểu Diễn Nghệ Thuật Trương Hồng Minh</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default EventDetail;