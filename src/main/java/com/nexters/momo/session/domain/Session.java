package com.nexters.momo.session.domain;

import com.nexters.momo.session.dto.PostSessionReqDto;
import com.nexters.momo.session.exception.InvalidSessionTimeException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;

import static java.time.temporal.ChronoUnit.MINUTES;

/**
 * 각 주차 세션을 나타내는 Session 엔티티입니다.
 * TODO : Generation 엔티티 연관 관계 매핑
 *
 * @author CHO Min Ho
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "session_id")
    private Long id;

    @Column(name = "session_title")
    private String title;

    @Column(name = "start_at")
    private LocalDateTime sessionStartTime;

    @Column(name = "end_at")
    private LocalDateTime sessionEndTime;

    @Column(name = "attendance_code")
    private Integer attendanceCode;

    @Column(name = "publish_at")
    private LocalDateTime sessionPublishTime;

    @Column(name = "session_status")
    @Enumerated(EnumType.STRING)
    private SessionStatus status;

    @Column(name = "session_address", columnDefinition = "TEXT")
    private String sessionAddress;

    private Session(String title, LocalDateTime sessionStartTime, LocalDateTime sessionEndTime, String sessionAddress) {
        this.title = title;
        this.sessionStartTime = sessionStartTime;
        this.sessionEndTime = sessionEndTime;
        this.attendanceCode = generateAttendanceCode();
        this.sessionPublishTime = LocalDateTime.now();
        this.status = SessionStatus.BEFORE;
        this.sessionAddress = sessionAddress;
    }

    /**
     * 출석 코드가 해당 세션의 출석 코드와 일치하는지 여부를 반환합니다.
     * @param attendanceCode 출석 코드
     * @return 해당 세션의 출석 코드와의 일치 여부
     */
    public boolean isSameAttendanceCode(int attendanceCode) {
        if (this.attendanceCode != attendanceCode) {
            return false;
        }
        return true;
    }

    /**
     * 분 단위 비교를 위해 세션 시작 시간을 Minute 단위로 변환하여 반환합니다.
     * @return Minute 단위로 변환된 세션 시작 시간
     */
    public LocalDateTime getStartTimeByMinute() {
        return sessionStartTime.truncatedTo(MINUTES);
    }

    /**
     * 분 단위 비교를 위해 세션 완료 시간을 Minute 단위로 변환하여 반환합니다.
     * @return Minute 단위로 변환된 세션 완료 시간
     */
    public LocalDateTime getEndTimeByMinute() {
        return sessionEndTime.truncatedTo(MINUTES);
    }


    /**
     * Session 엔티티를 생성하는 static 메서드입니다.
     * Session 엔티티는 해당 메서드를 이용해서만 생성됩니다.
     * @return 생성된 session 엔티티
     */
    public static Session createSession(PostSessionReqDto dto) {
        if (dto.getStartAt().isAfter(dto.getEndAt())) {
            // 세션 종료 시각이 세션 시작 시각보다 앞설 경우
            throw new InvalidSessionTimeException();
        }
        return new Session(dto.getTitle(), dto.getStartAt(), dto.getEndAt(), dto.getSessionAddress());
    }

    /**
     * 출석코드 (랜덤 정수 3자리)를 생성합니다.
     * @return 생성된 3자리 랜덤 코드
     */
    private int generateAttendanceCode() {
        return (int)(Math.random() * (999 - 100 + 1)) + 100;
    }
}
