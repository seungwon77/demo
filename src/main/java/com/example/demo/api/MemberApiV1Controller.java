package com.example.demo.api;

import com.example.demo.domain.ApiResponseV1;
import com.example.demo.domain.MemberV1;
import com.example.demo.domain.enums.ApiResponseCode;
import com.example.demo.service.MemberService;
import com.example.demo.validator.MemberCreateValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
public class MemberApiV1Controller {

    private final MemberService memberService;

    private final MemberCreateValidator memberCreateValidator;

    @GetMapping("/api/v1/member/{memberNo}")
    public ApiResponseV1<MemberV1> findMember(@PathVariable Long memberNo) {
        Optional<MemberV1> memberV1 = memberService.findMemberByMemberNo(memberNo);
        return memberV1.map(m -> this.getSuccessResponse(m)).orElse(this.getErrorResponse(ApiResponseCode.NOT_FOUND));
    }

    @PostMapping("/api/v1/member")
    public ApiResponseV1 createMember(@RequestBody MemberV1 memberV1, BindingResult errors) {
        // 입력 값 검증
        memberCreateValidator.validate(memberV1, errors);
        if (errors.hasErrors()) {
            String message = errors.getAllErrors().stream().findFirst().map(e -> e.getDefaultMessage()).orElse("입력 값 오류 발생");
            return this.getErrorResponse(ApiResponseCode.BAD_REQUEST.getCode(), message);
        }

        boolean isSuccess = memberService.createMember(memberV1);
        if (!isSuccess) {
            return this.getErrorResponse(ApiResponseCode.SERVER_ERROR);
        }
        return this.getSuccessResponse(null);
    }

    private <T> ApiResponseV1 getSuccessResponse(T data) {
        return ApiResponseV1.<T>builder()
                .code(ApiResponseCode.SUCCESS.getCode())
                .message(ApiResponseCode.SUCCESS.getMessage())
                .data(data)
                .build();
    }

    private ApiResponseV1 getErrorResponse(ApiResponseCode responseCode) {
        return this.getErrorResponse(responseCode.getCode(), responseCode.getMessage());
    }

    private ApiResponseV1 getErrorResponse(String code, String message) {
        return ApiResponseV1.builder()
                .code(code)
                .message(message)
                .build();
    }
}
