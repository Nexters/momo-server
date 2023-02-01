package com.nexters.momo.common.response;

import lombok.Getter;

@Getter
public class BaseResponse<T> {

    private final int code;
    private final String message;
    private final T data;

    public BaseResponse(ResponseCodeAndMessages response, T data) {
        this(response.getCode(), response.getMessage(), data);
    }

    public BaseResponse(ResponseCodeAndMessages response) {
        this(response.getCode(), response.getMessage(), null);
    }

    public BaseResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}

