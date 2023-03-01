package com.nexters.momo.common.data;

import com.nexters.momo.member.auth.domain.Member;
import com.nexters.momo.member.auth.domain.MemberRepository;
import com.nexters.momo.member.auth.domain.Occupation;
import com.nexters.momo.member.auth.domain.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataLoader {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    public void loadData() {
        log.debug("[call DataLoader]");

        Member adminMember = createAdminMember();
        memberRepository.save(adminMember);

        log.debug("[init done DataLoader]");
    }

    private Member createAdminMember() {
        String encodedPassword = passwordEncoder.encode("admin_password");
        return new Member("admin@nexters.com", encodedPassword, "admin", "admin_uuid", Role.ADMIN, Occupation.DEVELOPER);
    }
}
