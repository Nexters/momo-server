package com.nexters.momo.generation.presentation;

import com.nexters.momo.generation.application.dto.GenerationDto;
import com.nexters.momo.generation.domain.Generation;
import com.nexters.momo.generation.domain.GenerationRepository;
import com.nexters.momo.generation.domain.SignupCode;
import com.nexters.momo.generation.presentation.model.GenerationRequest;
import com.nexters.momo.support.RandomPortConfigure;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import static com.nexters.momo.generation.presentation.GenerationStep.*;

@DisplayName("기수 통합 테스트")
public class GenerationIntegrationTest extends RandomPortConfigure {

    @Autowired
    private GenerationRepository generationRepository;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        this.generationRepository.deleteAll();
    }

    /**
     * Given 생성하고 싶은 기수의 정보를 입력한다.
     * When 기수 생성을 요청한다.
     * Then 기수 생성이 완료된다.
     */
    @DisplayName("기수 생성 테스트")
    @Test
    void generation_create_test() {
        // given
        GenerationRequest request = new GenerationRequest(new GenerationDto(22, "signupCode"));

        // when
        ExtractableResponse<Response> response = 기수_생성_요청(request);

        // then
        기수_생성_확인(response, HttpStatus.CREATED);
    }

    /**
     * Given 비활성화하고 싶은 기수가 있다.
     * When 비활성화하고 싶은 기수의 id와 함께 요청한다
     * Then 해당 기수가 비활성화 된다.
     */
    @DisplayName("기수 비활성화 테스트")
    @Test
    void generation_deactivate_test() {
        // given
        SignupCode code = SignupCode.from("signup_code");
        Generation generation = Generation.of(22, code, true);
        Generation expected = generationRepository.save(generation);

        // when
        ExtractableResponse<Response> response = 기수_비활성화_요청(expected.getGenerationId());

        // then
        기수_비활성화_확인(response, HttpStatus.OK);
    }
}
