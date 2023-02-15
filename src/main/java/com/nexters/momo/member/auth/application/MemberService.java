package com.nexters.momo.member.auth.application;

import com.nexters.momo.member.auth.domain.Member;
import com.nexters.momo.member.auth.domain.MemberRepository;
import com.nexters.momo.member.auth.exception.DuplicatedUserDeviceIdException;
import com.nexters.momo.member.auth.exception.DuplicatedUserEmailException;
import com.nexters.momo.member.auth.exception.UserNotFoundException;
import com.nexters.momo.member.auth.presentation.dto.MemberRegisterRequest;
import com.nexters.momo.member.mypage.common.dto.response.AttendanceDto;
import com.nexters.momo.member.mypage.common.dto.response.MemberInfoDto;
import com.nexters.momo.member.mypage.common.dto.response.MemberLookUpResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public void register(MemberRegisterRequest memberRegisterRequest) {
        checkAlreadyExistsUser(memberRegisterRequest.getEmail(), memberRegisterRequest.getUuid());
        Member newMember = createMember(memberRegisterRequest);
        memberRepository.save(newMember);
    }

    @Transactional(readOnly = true)
    public MemberLookUpResponse findUserWithDetailInfo(Long memberId) {
        MemberInfoDto findMemberInfoDto = memberRepository.findMemberInfoById(memberId)
                .orElseThrow(UserNotFoundException::new);

        List<AttendanceDto> attendanceDtoList = memberRepository.findMemberAttendanceInfoById(memberId);

        return MemberLookUpResponse.of(findMemberInfoDto, attendanceDtoList);
    }

    private Member createMember(MemberRegisterRequest request) {
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        return Member.createWithRoleUser(request.getEmail(), encodedPassword, request.getName(), request.getUuid(), request.getOccupation());
    }

    private void checkAlreadyExistsUser(String email, String uuid) {
        checkAlreadyExistsEmail(email);
        checkAlreadyExistsDeviceUuid(uuid);
    }

    private void checkAlreadyExistsEmail(String email) {
        memberRepository.findByEmail(email)
                .ifPresent(user -> {
                    throw new DuplicatedUserEmailException();
                });
    }

    private void checkAlreadyExistsDeviceUuid(String uuid) {
        memberRepository.findByDeviceUniqueId(uuid)
                .ifPresent(user -> {
                    throw new DuplicatedUserDeviceIdException();
                });
    }
}
