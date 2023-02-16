package com.nexters.momo.member.auth.utils;

import com.nexters.momo.member.auth.application.RoleHierarchyService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityInitializer implements ApplicationRunner {

    private final RoleHierarchyService roleHierarchyService;

    private final RoleHierarchyImpl roleHierarchy;

    @Override
    public void run(ApplicationArguments args) {
        String allHierarchy = roleHierarchyService.AllHierarchyToString();
        roleHierarchy.setHierarchy(allHierarchy);
    }
}
