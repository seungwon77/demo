package com.example.demo.domain;

import com.example.demo.domain.enums.ApiResponseCode;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponseV1<T> {
    private String code;
    private String message;
    private T data;

    public ApiResponseV1(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public ApiResponseV1(ApiResponseCode responseCode) {
        this(responseCode, null);
    }

    public ApiResponseV1(ApiResponseCode responseCode, T data) {
        this.code = responseCode.getCode();
        this.message = responseCode.getMessage();
        this.data = data;
    }
}
