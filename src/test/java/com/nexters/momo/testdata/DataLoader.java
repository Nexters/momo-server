package com.nexters.momo.testdata;

import com.nexters.momo.member.auth.business.MemberDetailsService;
import com.nexters.momo.member.auth.presentation.dto.MemberRegisterRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component("testDataLoader")
public class DataLoader {

    private static final Logger log = LoggerFactory.getLogger(DataLoader.class);

    private static final String USER_EMAIL = "user@email.com";
    private static final String USER_PASSWORD = "password";
    private static final String NAME = "user";
    private static final String DEVICE_UUID = "device_uuid";

    private final MemberDetailsService memberDetailsService;

    public DataLoader(MemberDetailsService memberDetailsService) {
        this.memberDetailsService = memberDetailsService;
    }

    @Transactional
    public void loadData() {
        log.debug("[call DataLoader]");
        memberDetailsService.register(new MemberRegisterRequest(USER_EMAIL, USER_PASSWORD, NAME, 22, "developer", DEVICE_UUID));

        log.info("[init complete DataLoader]");
    }
}
