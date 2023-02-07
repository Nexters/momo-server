package com.nexters.momo.session.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Session 엔티티를 수정할 때 사용되는 DTO 클래스입니다.
 *
 * @author CHO Min Ho
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateSessionReqDto {
    @NotNull(message = "세션 ID를 적어주세요!")
    private Long sessionId;

    private String keyword;

    private String content;

    @NotNull(message = "세션 시작 시간을 적어주세요!")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startAt;

    @NotNull(message = "세션 마감 시간을 적어주세요!")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endAt;

    @NotNull(message = "세션 주소를 적어주세요!")
    private String sessionAddress;

    @NotNull(message = "출석 가능 시작 시간을 적어주세요!")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime attendanceStartAt;

    @NotNull(message = "출석 가능 마감 시간을 적어주세요!")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime attendanceEndAt;
}
