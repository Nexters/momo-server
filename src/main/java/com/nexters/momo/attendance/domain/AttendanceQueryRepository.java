package com.nexters.momo.attendance.domain;

import com.nexters.momo.attendance.presentation.dto.MemberAttendanceDto;

import java.util.List;

public interface AttendanceQueryRepository {

    List<MemberAttendanceDto> searchAttendanceListByWeek(Integer week);
}
