package com.nexters.momo.attendance.application;

import com.nexters.momo.attendance.application.dto.AttendanceDto;
import com.nexters.momo.attendance.domain.Attendance;
import com.nexters.momo.attendance.domain.AttendanceRepository;
import com.nexters.momo.attendance.exception.InvalidAttendanceCodeException;
import com.nexters.momo.session.domain.Session;
import com.nexters.momo.session.domain.SessionRepository;
import com.nexters.momo.session.exception.InvalidSessionIdException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AttendanceService {

    private final SessionRepository sessionRepository;
    private final AttendanceRepository attendanceRepository;

    public void attend(Long memberId, AttendanceDto attendanceDto) {
        Attendance attendance = createAttendance(memberId, attendanceDto);
        attendanceRepository.save(attendance);
    }

    private Attendance createAttendance(Long memberId, AttendanceDto attendanceDto) {
        Session findSession = sessionRepository.findById(attendanceDto.getSessionId())
                .orElseThrow(InvalidSessionIdException::new);

        if(!findSession.isSameAttendanceCode(attendanceDto.getAttendanceCode())) {
            throw new InvalidAttendanceCodeException();
        }

        return Attendance.createAttendance(memberId, findSession.getId(), findSession.judgeAttendanceStatus());
    }
}

