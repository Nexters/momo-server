package com.nexters.momo.member.auth.business;

import com.nexters.momo.member.auth.domain.Member;
import com.nexters.momo.member.auth.domain.MemberRepository;
import com.nexters.momo.member.auth.exception.DuplicatedUserDeviceIdException;
import com.nexters.momo.member.auth.exception.DuplicatedUserEmailException;
import com.nexters.momo.member.auth.exception.UserNotFoundException;
import com.nexters.momo.member.auth.presentation.dto.MemberRegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member findMember = memberRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);

        return new MemberContext(findMember, findMember.getAuthorities());
    }

    public void register(MemberRegisterRequest memberRegisterRequest) {
        checkAlreadyExistsUser(memberRegisterRequest.getEmail(), memberRegisterRequest.getUuid());
        Member newMember = createMember(memberRegisterRequest);
        memberRepository.save(newMember);
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
