package com.nexters.momo.attendance.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberAttendanceResponse {

    List<MemberAttendanceDto> memberAttendanceList;
}
