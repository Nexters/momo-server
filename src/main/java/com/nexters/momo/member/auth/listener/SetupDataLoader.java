package com.nexters.momo.member.auth.listener;

import com.nexters.momo.member.auth.domain.Role;
import com.nexters.momo.member.auth.domain.RoleHierarchy;
import com.nexters.momo.member.auth.domain.RoleHierarchyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private final RoleHierarchyRepository roleHierarchyRepository;

    private boolean alreadySetup = false;

    @Transactional
    @Override
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        if (alreadySetup) {
            return;
        }

        setupSecurityResources();
        alreadySetup = true;
    }

    private void setupSecurityResources() {
        createRoleHierarchyIfNotFound(Role.USER, Role.ADMIN);
        createRoleHierarchyIfNotFound(Role.ANONYMOUS, Role.USER);
    }

    private void createRoleHierarchyIfNotFound(Role childRole, Role parentRole) {
        RoleHierarchy parentRoleHierarchy = getRoleHierarchy(parentRole);
        RoleHierarchy childRoleHierarchy = getRoleHierarchy(childRole);
        childRoleHierarchy.setParentName(parentRoleHierarchy);
    }

    private RoleHierarchy getRoleHierarchy(Role role) {
        RoleHierarchy roleHierarchy = roleHierarchyRepository.findByChildName(role.getRole())
                .orElseGet(() -> RoleHierarchy.createWithChildName(role.getRole()));

        return roleHierarchyRepository.save(roleHierarchy);
    }
}

