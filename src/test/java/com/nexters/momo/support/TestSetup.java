package com.nexters.momo.support;

import com.nexters.momo.common.TestRedisContainer;
import com.nexters.momo.member.auth.domain.Member;
import com.nexters.momo.member.auth.domain.MemberRepository;
import com.nexters.momo.member.auth.domain.Occupation;
import com.nexters.momo.member.auth.domain.Role;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestSetup extends TestRedisContainer {

    protected static final String SIGNUP_CODE = "signup_code";

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @LocalServerPort
    int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
        Member adminMember = createAdminMember();
        memberRepository.save(adminMember);
    }

    @AfterEach
    public void tearDown() {
        this.memberRepository.deleteAll();
    }

    private Member createAdminMember() {
        String encodedPassword = passwordEncoder.encode("admin_password");
        return new Member("admin@naver.com", encodedPassword, "admin", "admin_uuid", Role.ADMIN, Occupation.DEVELOPER);
    }
}
