package com.nexters.momo.generation.presentation.model;

import com.nexters.momo.generation.application.dto.GenerationDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GenerationRequest {
    private GenerationDto generation;
}
