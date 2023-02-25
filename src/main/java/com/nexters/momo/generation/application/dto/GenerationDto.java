package com.nexters.momo.generation.application.dto;

import com.nexters.momo.generation.domain.Generation;
import com.nexters.momo.generation.domain.SignupCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GenerationDto {
    private long id;
    private int number;

    private SignupCode signupCode;

    public static GenerationDto from(Generation entity) {
        return new GenerationDto(entity.getGenerationId(), entity.getNumber(), entity.getSignupCode());
    }
}
