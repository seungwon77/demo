package com.example.demo.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiErrorV1 {
    @ApiModelProperty(notes = "Invalid Field Name", example = "email", required = true)
    private String field;
    @ApiModelProperty(notes = "Error Message", example = "이메일 형식 오류", required = true)
    private String errorMessage;
}
