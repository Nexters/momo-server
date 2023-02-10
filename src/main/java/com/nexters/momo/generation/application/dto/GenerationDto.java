package com.nexters.momo.generation.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GenerationDto {
    private Long id;

    private int number;

    @NotNull
    private String signupCode;

    private boolean active;

    public GenerationDto(int number, String signupCode) {
        this(null, number, signupCode, false);
    }
}
