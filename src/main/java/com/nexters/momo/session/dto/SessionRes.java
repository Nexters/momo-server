package com.nexters.momo.session.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Session 정보 반환을 위한 Response 객체입니다.
 *
 * @author CHO Min Ho
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SessionRes {

    SessionDto session;

    Long id;

    int week;

}
