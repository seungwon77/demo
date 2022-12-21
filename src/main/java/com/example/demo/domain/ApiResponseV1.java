package com.example.demo.domain;

import com.example.demo.domain.enums.ApiResponseCode;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponseV1<T> {
    @ApiModelProperty(notes = "응답코드 (성공 200)", example = "200")
    private String code;

    @ApiModelProperty(notes = "응답메시지", example = "SUCCESS")
    private String message;

    @ApiModelProperty(notes = "응답시각", example = "2022-12-12 09:00:00")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;

    @ApiModelProperty(notes = "응답 결과")
    private T data;

    @ApiModelProperty(notes = "오류 필드 및 메시지")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<ApiErrorV1> errors;
}
