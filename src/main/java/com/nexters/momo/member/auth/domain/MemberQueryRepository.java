package com.nexters.momo.member.auth.domain;

import com.nexters.momo.member.mypage.common.dto.response.AttendanceDto;
import com.nexters.momo.member.mypage.common.dto.response.MemberInfoDto;

import java.util.List;
import java.util.Optional;

public interface MemberQueryRepository {

    Optional<MemberInfoDto> findMemberInfoById(Long memberId);

    List<AttendanceDto> findMemberAttendanceInfoById(Long memberId);
}
