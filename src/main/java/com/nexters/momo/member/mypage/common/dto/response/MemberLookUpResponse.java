package com.nexters.momo.member.mypage.common.dto.response;

import com.nexters.momo.attendance.domain.AttendanceStatus;
import com.nexters.momo.member.auth.domain.Occupation;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class MemberLookUpResponse {

    private boolean active;

    private String name;

    private String email;

    private Occupation occupation;

    private Long absentCount;

    private Long attendanceCount;

    private Long lateCount;

    private List<AttendanceDto> attendanceHistories;

    private MemberLookUpResponse(boolean active, String name, String email, Occupation occupation, Long absentCount, Long attendanceCount, Long lateCount, List<AttendanceDto> attendanceHistories) {
        this.active = active;
        this.name = name;
        this.email = email;
        this.occupation = occupation;
        this.absentCount = absentCount;
        this.attendanceCount = attendanceCount;
        this.lateCount = lateCount;
        this.attendanceHistories = attendanceHistories;
    }

    public static MemberLookUpResponse of(MemberInfoDto info, List<AttendanceDto> attendanceList) {
        long absentCnt = 0;
        long attendanceCnt = 0;
        long lateCnt = 0;

        for (AttendanceDto attendanceDto : attendanceList) {
            if (attendanceDto.getAttendanceStatus().equals(AttendanceStatus.ABSENT)) {
                absentCnt += 1;
            } else if (attendanceDto.getAttendanceStatus().equals(AttendanceStatus.ATTENDANCE)) {
                attendanceCnt += 1;
            } else if (attendanceDto.getAttendanceStatus().equals(AttendanceStatus.LATE)) {
                lateCnt += 1;
            }
        }

        return new MemberLookUpResponse(info.isActive(), info.getName(), info.getEmail(), info.getOccupation(), absentCnt, attendanceCnt, lateCnt, attendanceList);
    }
}
