package com.nexters.momo.attendance.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceDto {

    private Long sessionId;

    private Integer attendanceCode;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime attendanceAt;

    public static AttendanceDto of(Long sessionId, Integer attendanceCode, LocalDateTime attendanceAt) {
        return new AttendanceDto(sessionId, attendanceCode, attendanceAt);
    }
}
