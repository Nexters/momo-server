package com.nexters.momo.generation.presentation;

import com.nexters.momo.common.response.BaseResponse;
import com.nexters.momo.generation.application.GenerationService;
import com.nexters.momo.generation.presentation.model.GenerationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/generations")
@RequiredArgsConstructor
public class GenerationController {

    private final GenerationService generationService;

    @PostMapping
    public ResponseEntity<BaseResponse<Void>> create(@RequestBody GenerationRequest request) {
        generationService.create(request.getGeneration());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new BaseResponse<>(HttpStatus.CREATED.value(), "기수 생성 성공", null));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> deactivate(@PathVariable Long id) {
        generationService.deactivate(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponse<>(HttpStatus.OK.value(), "기수 비활성화 성공", null));
    }

}
