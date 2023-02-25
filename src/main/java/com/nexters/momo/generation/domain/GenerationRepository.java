package com.nexters.momo.generation.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GenerationRepository extends JpaRepository<Generation, Long> {
    Optional<Generation> findByActiveIsTrue();
}
