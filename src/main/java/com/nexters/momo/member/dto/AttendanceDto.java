package com.nexters.momo.member.mypage.common.dto.response;

import com.nexters.momo.attendance.domain.AttendanceStatus;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class AttendanceDto {

    private AttendanceStatus attendanceStatus;

    private LocalDateTime attendanceTime;

    @QueryProjection
    public AttendanceDto(AttendanceStatus attendanceStatus, LocalDateTime attendanceTime) {
        this.attendanceStatus = attendanceStatus;
        this.attendanceTime = attendanceTime;
    }
}
