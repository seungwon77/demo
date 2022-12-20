package com.example.demo.domain;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiErrorV1 {
    private String field;
    private String errorMessage;
}
