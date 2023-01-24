package com.nexters.momo.session.domain;

import com.nexters.momo.session.dto.PostSessionReqDTO;
import com.nexters.momo.session.exception.InvalidSessionTimeException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;

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
     * Session 엔티티를 생성하는 static 메서드입니다.
     * Session 엔티티는 해당 메서드를 이용해서만 생성됩니다.
     * @return 생성된 session 엔티티
     */
    public static Session createSession(PostSessionReqDTO dto) {
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
