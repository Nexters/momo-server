package com.nexters.momo.attendance.domain;

import com.nexters.momo.attendance.presentation.dto.MemberAttendanceDto;
import com.nexters.momo.attendance.presentation.dto.QMemberAttendanceDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.nexters.momo.attendance.domain.QAttendance.attendance;
import static com.nexters.momo.member.auth.domain.QMember.member;
import static com.nexters.momo.session.domain.QSession.session;

@RequiredArgsConstructor
public class AttendanceQueryRepositoryImpl implements AttendanceQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<MemberAttendanceDto> searchAttendanceListByWeek(Integer week) {
        return queryFactory
                .select(new QMemberAttendanceDto(member.name.value, member.occupation, attendance.status))
                .from(attendance)
                .join(session).on(attendance.sessionId.eq(session.id))
                .join(member).on(attendance.memberId.eq(member.id))
                .where((session.week.eq(week)))
                .fetch();
    }
}
