package com.example.demo.api;

import com.example.demo.domain.ApiErrorV1;
import com.example.demo.domain.ApiResponseV1;
import com.example.demo.domain.MemberV1;
import com.example.demo.domain.enums.ApiResponseCode;
import com.example.demo.service.MemberService;
import com.example.demo.validator.MemberCreateValidator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Api(value = "MEMBER")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class MemberApiControllerV1 {

    private final MemberService memberService;

    private final MemberCreateValidator memberCreateValidator;

    @ApiOperation(value = "회원 조회", notes = "회원번호로 회원정보를 조회한다.")
    @GetMapping("/member/{memberNo}")
    public ResponseEntity<ApiResponseV1<MemberV1>> findMember(@PathVariable Long memberNo) {
        Optional<MemberV1> memberV1 = memberService.findMemberByMemberNo(memberNo);
        if (memberV1.isPresent()) {
            return ResponseEntity.ok(this.getSuccessResponse(memberV1.get()));
        } else {
            return ResponseEntity.ok(this.getErrorResponse(ApiResponseCode.NOT_FOUND));
        }
    }

    @ApiOperation(value = "회원 등록", notes = "회원 정보를 등록한다.")
    @PostMapping("/member")
    public ResponseEntity<ApiResponseV1> createMember(@RequestBody MemberV1 memberV1, BindingResult errors) {
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
                .timestamp(LocalDateTime.now())
                .data(data)
                .build();
    }

    private ApiResponseV1 getErrorResponse(ApiResponseCode responseCode) {
        return this.getErrorResponse(responseCode, null);
    }

    private ApiResponseV1 getErrorResponse(ApiResponseCode responseCode, List<FieldError> errors) {
        return this.getErrorResponse(responseCode.getCode(), responseCode.getMessage(), errors);
    }

    private ApiResponseV1 getErrorResponse(String code, String message, List<FieldError> errors) {
        List<ApiErrorV1> errorList = Objects.isNull(errors) ? null : errors.stream()
                .map(error -> ApiErrorV1.builder()
                        .field(error.getField())
                        .errorMessage(error.getDefaultMessage())
                        .build())
                .collect(Collectors.toList());
        return ApiResponseV1.builder()
                .code(code)
                .message(message)
                .timestamp(LocalDateTime.now())
                .errors(errorList)
                .build();
    }
}
