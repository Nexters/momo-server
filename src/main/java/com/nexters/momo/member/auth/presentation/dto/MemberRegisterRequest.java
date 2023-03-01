package com.nexters.momo.member.auth.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberRegisterRequest {

        @NotBlank
        private String email;

        @NotBlank
        private String password;

        @NotBlank
        private String name;

        @NotNull
        private Integer generation;

        @NotBlank
        private String occupation;

        @NotBlank
        private String uuid;

        @NotBlank
        private String signupCode;
}
