package com.nexters.momo.member.auth.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleHierarchyRepository extends JpaRepository<RoleHierarchy, Long> {

    Optional<RoleHierarchy> findByChildName(String roleName);
}
