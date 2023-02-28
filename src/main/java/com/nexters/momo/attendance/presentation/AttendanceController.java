package com.nexters.momo.attendance.presentation;

import com.nexters.momo.attendance.application.AttendanceService;
import com.nexters.momo.attendance.application.dto.AttendanceDto;
import com.nexters.momo.attendance.presentation.dto.MemberAttendanceDto;
import com.nexters.momo.attendance.presentation.dto.MemberAttendanceRequest;
import com.nexters.momo.attendance.presentation.dto.MemberAttendanceResponse;
import com.nexters.momo.member.auth.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/attendances")
public class AttendanceController implements AttendanceApiSpec {

    private final AttendanceService attendanceService;

    @Override
    @PostMapping
    public ResponseEntity attendSession(@Valid @RequestBody MemberAttendanceRequest request, @AuthenticationPrincipal Member member) {
        AttendanceDto attendanceDto = toDto(request);
        attendanceService.attend(member.getId(), attendanceDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("{week}")
    public ResponseEntity searchAttendanceList(@PathVariable Integer week) {
        List<MemberAttendanceDto> attendanceDtoList = attendanceService.searchAttendanceListByWeek(week);
        return ResponseEntity.ok().body(new MemberAttendanceResponse(attendanceDtoList));
    }

    private AttendanceDto toDto(MemberAttendanceRequest request) {
        return AttendanceDto.of(request.getSessionId(), request.getAttendanceCode(), request.getAttendanceAt());
    }
}
