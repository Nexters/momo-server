package com.nexters.momo.generation.application;

import com.nexters.momo.common.response.ErrorCodeAndMessages;
import com.nexters.momo.generation.application.dto.GenerationDto;
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

    @Transactional(readOnly = true)
    public GenerationDto getActiveGeneration() {
        Generation current = generationRepository.findByActiveIsTrue()
                .orElseThrow(() -> new IllegalStateException("can not found active generation"));
        return GenerationDto.from(current);
    }

    public boolean isPresentActive() {
        try {
            return getActiveGeneration() != null;
        } catch (IllegalStateException ex) {
            return false;
        }
    }

    public void validGenerationCode(String codeValue) {
        SignupCode code = SignupCode.from(codeValue);
        SignupCode activeCode = getActiveGeneration().getSignupCode();
        if (!isSame(code, activeCode)) {
            throw new IllegalArgumentException(ErrorCodeAndMessages.INVALID_GENERATION_CODE.getMessage());
        }
    }

    private boolean isSame(SignupCode code1, SignupCode code2) {
        return code1.equals(code2);
    }

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
