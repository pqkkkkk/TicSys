package com.example.ticsys.event.dto.request;

import java.sql.Time;
import java.time.LocalDate;

import org.springframework.web.multipart.MultipartFile;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventRequest {
     int id;
    int organizerId;
    String location;
    String description;
    MultipartFile bannerPath;
    MultipartFile seatMapPath;
    String name;
    String status;
    String category;
    LocalDate date;
    Time time;
}
