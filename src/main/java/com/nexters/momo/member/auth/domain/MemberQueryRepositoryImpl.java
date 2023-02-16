package com.nexters.momo.member.auth.domain;

import com.nexters.momo.member.mypage.common.dto.response.AttendanceDto;
import com.nexters.momo.member.mypage.common.dto.response.MemberInfoDto;
import com.nexters.momo.member.mypage.common.dto.response.QAttendanceDto;
import com.nexters.momo.member.mypage.common.dto.response.QMemberInfoDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import static com.nexters.momo.attendance.domain.QAttendance.attendance;
import static com.nexters.momo.member.auth.domain.QMember.member;

@RequiredArgsConstructor
public class MemberQueryRepositoryImpl implements MemberQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<MemberInfoDto> findMemberInfoById(Long memberId) {
        MemberInfoDto memberInfoDto = queryFactory
                .select(new QMemberInfoDto(member.active, member.name.value, member.email.value, member.occupation))
                .from(member)
                .where(member.id.eq(memberId))
                .fetchOne();

        return Optional.of(memberInfoDto);
    }

    @Override
    public List<AttendanceDto> findMemberAttendanceInfoById(Long memberId) {
        return queryFactory
                .select(new QAttendanceDto(attendance.status, attendance.createTime))
                .from(attendance)
                .where(attendance.memberId.eq(memberId))
                .fetch();
    }
}
