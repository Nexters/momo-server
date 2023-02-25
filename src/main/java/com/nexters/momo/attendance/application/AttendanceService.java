package com.nexters.momo.attendance.application;

import com.nexters.momo.attendance.application.dto.AttendanceDto;
import com.nexters.momo.attendance.domain.Attendance;
import com.nexters.momo.attendance.domain.AttendanceRepository;
import com.nexters.momo.member.auth.domain.Member;
import com.nexters.momo.session.domain.Session;
import com.nexters.momo.session.domain.SessionRepository;
import com.nexters.momo.session.exception.InvalidSessionIdException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AttendanceService {

    private final SessionRepository sessionRepository;
    private final AttendanceRepository attendanceRepository;

    public void attend(Member member, AttendanceDto attendanceDto) {
        Session findSession = sessionRepository.findById(attendanceDto.getSessionId())
                .orElseThrow(InvalidSessionIdException::new);

        Attendance attendance = Attendance.createAttendance(findSession, member.getId(), attendanceDto.getAttendanceCode());
        attendanceRepository.save(attendance);
    }
}

