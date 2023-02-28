package com.nexters.momo.attendance.presentation.dto;

import com.nexters.momo.attendance.domain.AttendanceStatus;
import com.nexters.momo.member.auth.domain.Occupation;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberAttendanceDto {

    private String name;


    private Occupation occupation;

    private AttendanceStatus attendanceStatus;

    @QueryProjection
    public MemberAttendanceDto(String name, Occupation occupation, AttendanceStatus attendanceStatus) {
        this.name = name;
        this.occupation = occupation;
        this.attendanceStatus = attendanceStatus;
    }
}
