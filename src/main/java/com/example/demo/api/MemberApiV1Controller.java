package com.example.demo.api;

import com.example.demo.domain.ApiErrorResponseV1;
import com.example.demo.domain.ApiErrorV1;
import com.example.demo.domain.ApiResponseV1;
import com.example.demo.domain.MemberV1;
import com.example.demo.domain.enums.ApiResponseCode;
import com.example.demo.service.MemberService;
import com.example.demo.validator.MemberCreateValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.ListUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
public class MemberApiV1Controller {

    private final MemberService memberService;

    private final MemberCreateValidator memberCreateValidator;

    @GetMapping("/api/v1/member/{memberNo}")
    public ResponseEntity findMember(@PathVariable Long memberNo) {
        Optional<MemberV1> memberV1 = memberService.findMemberByMemberNo(memberNo);
        if (memberV1.isPresent()) {
            return ResponseEntity.ok(this.getSuccessResponse(memberV1.get()));
        } else {
            return ResponseEntity.ok(this.getErrorResponse(ApiResponseCode.NOT_FOUND));
        }
    }

    @PostMapping("/api/v1/member")
    public ResponseEntity createMember(@RequestBody MemberV1 memberV1, BindingResult errors) {
        memberCreateValidator.validate(memberV1, errors);
        if (errors.hasErrors()) {
            return ResponseEntity.ok(this.getErrorResponse(ApiResponseCode.BAD_REQUEST, errors.getFieldErrors()));
        }
        boolean isSuccess = memberService.createMember(memberV1);
        if (!isSuccess) {
            return ResponseEntity.ok(this.getErrorResponse(ApiResponseCode.SERVER_ERROR));
        }
        return ResponseEntity.ok(this.getSuccessResponse());
    }

    private ApiResponseV1 getSuccessResponse() {
        return this.getSuccessResponse(null);
    }

    private <T> ApiResponseV1 getSuccessResponse(T data) {
        return ApiResponseV1.<T>builder()
                .code(ApiResponseCode.SUCCESS.getCode())
                .message(ApiResponseCode.SUCCESS.getMessage())
                .data(data)
                .build();
    }

    private ApiErrorResponseV1 getErrorResponse(ApiResponseCode responseCode) {
        return this.getErrorResponse(responseCode, null);
    }

    private ApiErrorResponseV1 getErrorResponse(ApiResponseCode responseCode, List<FieldError> errors) {
        return this.getErrorResponse(responseCode.getCode(), responseCode.getMessage(), errors);
    }

    private ApiErrorResponseV1 getErrorResponse(String code, String message, List<FieldError> errors) {
        List<ApiErrorV1> errorList = ListUtils.emptyIfNull(errors).stream()
                .map(error -> ApiErrorV1.builder().field(error.getField()).errorMessage(error.getDefaultMessage()).build())
                .collect(Collectors.toList());
        return ApiErrorResponseV1.builder()
                .code(code)
                .message(message)
                .timestamp(LocalDateTime.now())
                .errors(errorList)
                .build();
    }
}
