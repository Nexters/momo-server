package com.nexters.momo.attendance.domain;

import com.nexters.momo.common.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
public class Attendance extends BaseTimeEntity {
    private static final int MINIMUM_ATTENDANCE_TIME = 30;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attendance_id", nullable = false)
    private Long id;

    @Column(name = "session_id", nullable = false)
    private Long sessionId;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "attendance_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private AttendanceStatus status;

    private Attendance(AttendanceStatus status, Long sessionId, Long memberId) {
        this.status = status;
        this.sessionId = sessionId;
        this.memberId = memberId;
    }

    /**
     * Attendance 엔티티를 생성하는 메서드입니다.
     * Attendance 엔티티는 해당 메서드로만 생성됩니다.
     * @return 생성된 Attendance 엔티티
     */
    public static Attendance createAttendance(Long memberId, Long sessionId, AttendanceStatus attendanceStatus) {
        return new Attendance(attendanceStatus, memberId, sessionId);
    }
}
