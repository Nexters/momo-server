package com.nexters.momo.session.application.dto;

import com.nexters.momo.session.domain.Point;
import com.nexters.momo.session.domain.Session;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Session 의 정보를 담고 있는 Dto 클래스입니다.
 *
 * @author CHO Min Ho
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SessionDto {

    private Long id;

    private String title;

    @NotNull(message = "몇주차인지 적어주세요!")
    private Integer week;

    private String content;

    @NotNull(message = "세션 시작 시간을 적어주세요!")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startAt;

    @NotNull(message = "세션 마감 시간을 적어주세요!")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endAt;

    @NotEmpty(message = "세션 주소를 적어주세요!")
    private String address;

    @NotEmpty(message = "세션 상세 주소를 적어주세요!")
    private String addressDetail;

    @NotEmpty
    private Point point;

    @NotNull(message = "출석 가능 시작 시간을 적어주세요!")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime attendanceStartedAt;

    @NotNull(message = "출석 가능 마감 시간을 적어주세요!")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime attendanceClosedAt;

    /**
     * Session Dto 클래스를 Session으로부터 만드는 메서드입니다.
     *
     * @param session session 엔티티
     * @return 생성된 dto 클래스
     */
    public static SessionDto from(Session session) {
        return new SessionDto(
                session.getId(),
                session.getTitle(),
                session.getWeek(),
                session.getContent(),
                session.getStartAt(),
                session.getEndAt(),
                session.getAddress(),
                session.getAddressDetail(),
                session.getPoint(),
                session.getAttendanceStartedAt(),
                session.getAttendanceClosedAt());
    }

    public static SessionDto of(
            Long id, String title, int week, String content,
            LocalDateTime startAt, LocalDateTime endAt, String address, String addressDetail, Point point,
            LocalDateTime attendanceStartedAt, LocalDateTime attendanceClosedAt
    ) {
        return new SessionDto(
                id,
                title,
                week,
                content,
                startAt,
                endAt,
                address,
                addressDetail,
                point,
                attendanceStartedAt,
                attendanceClosedAt
        );
    }

}
