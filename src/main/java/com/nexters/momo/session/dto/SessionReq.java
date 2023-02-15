package com.nexters.momo.session.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Session 생성을 위한 Request 객체입니다.
 *
 * @author CHO Min Ho
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SessionReq {

    // TODO - session 생성 시 필요한 데이터 확인
    private SessionDto session;

}
