package com.nexters.momo.session.presentation.dto;

import com.nexters.momo.session.domain.Point;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Session 생성을 위한 Request 객체입니다.
 *
 * @author CHO Min Ho
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SessionRequest {

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

}
