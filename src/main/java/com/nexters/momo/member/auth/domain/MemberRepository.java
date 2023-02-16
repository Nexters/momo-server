package com.nexters.momo.member.auth.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberQueryRepository {

    @Query("select m from Member m where m.email.value = :email")
    Optional<Member> findByEmail(@Param("email") String email);

    Optional<Member> findByDeviceUniqueId(String uuid);
}
