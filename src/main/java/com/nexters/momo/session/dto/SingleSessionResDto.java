package com.nexters.momo.session.dto;

import com.nexters.momo.session.domain.Session;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

/**
 * 단일 Session 엔티티의 정보를 반환하는 DTO 클래스입니다.
 *
 * @author CHO Min Ho
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SingleSessionResDto {
    private String keyword;

    private String content;

    private int sessionOrder;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startAt;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endAt;

    private String sessionAddress;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime attendanceStartAt;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime attendanceEndAt;

    /**
     * Session 엔티티를 이용해 Dto 객체를 생성하는 메서드입니다.
     * @param session 세션 엔티티
     * @return 생성된 Dto 객체
     */
    public static SingleSessionResDto from(Session session) {
        return new SingleSessionResDto(session.getSessionKeyword(), session.getSessionContent(),
                session.getSessionOrder(), session.getSessionStartTime(), session.getSessionEndTime(),
                session.getSessionAddress(), session.getAttendanceStartTime(), session.getAttendanceEndTime());
    }
}
