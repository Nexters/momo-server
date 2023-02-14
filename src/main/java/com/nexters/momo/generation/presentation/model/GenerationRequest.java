package com.nexters.momo.generation.presentation.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GenerationRequest {
    private int number;

    @NotNull
    private String signupCode;
}
