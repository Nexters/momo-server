package com.nexters.momo.session.domain;

import com.nexters.momo.session.exception.InvalidSessionTimeException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;


/**
 * 각 주차 세션을 나타내는 Session 엔티티입니다.
 * TODO : 타임라인 이미지 추가
 * TODO : 위치 좌표 추가
 *
 * @author CHO Min Ho
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "session_id", nullable = false)
    private Long id;

    @Column(name = "generation_id", nullable = false)
    private Long generationId;

    @Column
    private String title;

    @Column(nullable = false)
    private int week;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private LocalDateTime startAt;

    @Column(nullable = false)
    private LocalDateTime endAt;

    @Column(nullable = false)
    private Integer attendanceCode;

    @Column
    private LocalDateTime publishAt;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SessionStatus status;

    @Column(columnDefinition = "TEXT")
    private String address;

    @Column(nullable = false)
    private LocalDateTime attendanceStartedAt;

    @Column(nullable = false)
    private LocalDateTime attendanceClosedAt;

    private Session(Long generationId, String title, int week,
                    String content, LocalDateTime startAt, LocalDateTime endAt,
                    String address, LocalDateTime attendanceStartedAt,
                    LocalDateTime attendanceClosedAt) {
        this.generationId = generationId;
        this.title = title;
        this.week = week;
        this.content = content;
        this.startAt = startAt;
        this.endAt = endAt;
        this.attendanceCode = generateAttendanceCode();
        this.publishAt = LocalDateTime.now();
        this.status = SessionStatus.BEFORE;
        this.address = address;
        this.attendanceStartedAt = attendanceStartedAt;
        this.attendanceClosedAt = attendanceClosedAt;
    }

    /**
     * 출석 코드가 해당 세션의 출석 코드와 일치하는지 여부를 반환합니다.
     * @param attendanceCode 출석 코드
     * @return 해당 세션의 출석 코드와의 일치 여부
     */
    public boolean isSameAttendanceCode(int attendanceCode) {
        return this.attendanceCode == attendanceCode;
    }

    /**
     * Session 을 수정하는 메서드입니다.
     */
    public void updateSession(String title, String content, LocalDateTime startAt, LocalDateTime endAt,
                              String address, LocalDateTime attendanceStartedAt, LocalDateTime attendanceClosedAt) {
        this.title = title;
        this.content = content;
        this.startAt = startAt;
        this.endAt = endAt;
        this.address = address;
        this.attendanceStartedAt = attendanceStartedAt;
        this.attendanceClosedAt = attendanceClosedAt;
    }

    /**
     * Session 엔티티를 생성하는 static 메서드입니다.
     * Session 엔티티는 해당 메서드를 이용해서만 생성됩니다.
     * @return 생성된 session 엔티티
     */
    public static Session createSession(String title, Integer week, String content, LocalDateTime startAt, LocalDateTime endAt,
                                        String address, LocalDateTime attendanceStartedAt,
                                        LocalDateTime attendanceClosedAt, Long generationId) {
        if (startAt.isAfter(endAt)) {
            // 세션 종료 시각이 세션 시작 시각보다 앞설 경우
            throw new InvalidSessionTimeException();
        }
        return new Session(generationId, title, week, content,
                startAt, endAt, address, attendanceStartedAt, attendanceClosedAt);
    }

    /**
     * 출석코드 (랜덤 정수 3자리)를 생성합니다.
     * @return 생성된 3자리 랜덤 코드
     */
    private int generateAttendanceCode() {
        return (int)(Math.random() * (999 - 100 + 1)) + 100;
    }
}
