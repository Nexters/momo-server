package com.nexters.momo.session.presentation.dto;

import com.nexters.momo.session.application.dto.SessionDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 세션을 조회할 때 반환하는 Response 객체입니다.
 *
 * @author CHO Min Ho
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SessionResponse {

    private SessionDto sessionDto;

    private List<String> files;

}
