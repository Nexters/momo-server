package com.nexters.momo.attendance.domain;

import com.nexters.momo.attendance.exception.InvalidAttendanceCodeException;
import com.nexters.momo.attendance.exception.TooFastAttendanceTimeException;
import com.nexters.momo.session.domain.Session;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * 각 세션의 출석을 나타내는 Attendance 엔티티입니다.
 * TODO : Member 엔티티와 연관관계 매핑
 * TODO : 출석 엔티티를 미리 생성하고 출석체크 시에 status를 변경하는 방식으로 할 것인지, 출석체크 시에 엔티티를 생성하는 방식으로 할지 결정
 *
 * @author CHO Min Ho
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attendance_id")
    private Long id;

    @Column(name = "session_id")
    private Long sessionId;

    @Column(name = "attendance_status")
    @Enumerated(EnumType.STRING)
    private AttendanceStatus status;

    @Column(name = "created_at")
    private LocalDateTime attendanceCreatedTime;

    @Column(name = "last_modified_at")
    private LocalDateTime attendanceUpdatedTime;

    private Attendance(AttendanceStatus status, Long sessionId) {
        this.status = status;
        this.sessionId = sessionId;
        this.attendanceCreatedTime = LocalDateTime.now();
        this.attendanceUpdatedTime = LocalDateTime.now();
    }

    /**
     * Attendance 엔티티를 생성하는 메서드입니다.
     * Attendance 엔티티는 해당 메서드로만 생성됩니다.
     * @param session 해당 주차의 세션
     * @param attendanceCode 출석 코드
     * @return 생성된 Attendance 엔티티
     */
    public static Attendance createAttendance(Session session, Integer attendanceCode) {
        LocalDateTime currentTime = LocalDateTime.now();

        if (!Objects.equals(attendanceCode, session.getAttendanceCode())) {
            // 출석 코드 불일치
            throw new InvalidAttendanceCodeException();
        }

        LocalDateTime currentTimeByMinute = currentTime.truncatedTo(ChronoUnit.MINUTES);
        LocalDateTime sessionStartTimeByMinute = session.getSessionStartTime().truncatedTo(ChronoUnit.MINUTES);
        int compareResult = currentTimeByMinute.compareTo(sessionStartTimeByMinute);

        // 1. 아직 출석할 수 없는 시간일 경우 (30분 전)
        // TODO : 몇 분 전부터 출석이 가능하게 해야할지 의논 필요
        if (compareResult > 30) {
            throw new TooFastAttendanceTimeException();
        }

        // 2. 세션 종료 후 출석을 시도하는 경우 - 결석
        if (currentTime.isAfter(session.getSessionEndTime())) {
            return new Attendance(AttendanceStatus.ABSENT, session.getId());
        }

        // 3. 지각
        if (currentTime.isAfter(session.getSessionStartTime())) {
            return new Attendance(AttendanceStatus.LATE, session.getId());
        }

        // 4. 정상 출석
        return new Attendance(AttendanceStatus.ATTENDANCE, session.getId());

    }
}
