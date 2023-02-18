package com.nexters.momo.generation.presentation;

import com.nexters.momo.generation.application.GenerationService;
import com.nexters.momo.generation.presentation.dto.GenerationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/generations")
@RequiredArgsConstructor
public class GenerationController {

    private final GenerationService generationService;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid GenerationRequest request) {
        generationService.create(request.getSignupCode(), request.getNumber());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> deactivate(@PathVariable Long id) {
        generationService.deactivate(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
