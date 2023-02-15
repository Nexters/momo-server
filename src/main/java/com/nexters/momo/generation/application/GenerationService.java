package com.nexters.momo.generation.application;

import com.nexters.momo.generation.domain.Generation;
import com.nexters.momo.generation.domain.GenerationRepository;
import com.nexters.momo.generation.domain.SignupCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GenerationService {

    private final GenerationRepository generationRepository;

    @Transactional
    public void create(String signupCode, int number) {
        SignupCode code = SignupCode.from(signupCode);
        generationRepository.save(Generation.of(number, code, true));
    }

    @Transactional
    public void deactivate(Long id) {
        generationRepository.findById(id)
                .ifPresent(Generation::deactivate);
    }
}
