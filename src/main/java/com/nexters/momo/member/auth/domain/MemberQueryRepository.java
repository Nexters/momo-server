package com.nexters.momo.member.auth.domain;

import com.nexters.momo.member.dto.AttendanceDto;
import com.nexters.momo.member.dto.MemberInfoDto;

import java.util.List;
import java.util.Optional;

public interface MemberQueryRepository {

    Optional<MemberInfoDto> findMemberInfoById(Long memberId);

    List<AttendanceDto> findMemberAttendanceInfoById(Long memberId);
}
