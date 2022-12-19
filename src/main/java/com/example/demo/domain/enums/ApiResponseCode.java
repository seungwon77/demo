package com.example.demo.domain.enums;

import lombok.Getter;

@Getter
public enum ApiResponseCode {

    SUCCESS("200", "SUCCESS"),
    BAD_REQUEST("400", "BAD REQUEST"),
    NOT_FOUND("404", "NOT FOUND"),
    SERVER_ERROR("500", "SERVER ERROR");

    private String code;
    private String message;

    ApiResponseCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
