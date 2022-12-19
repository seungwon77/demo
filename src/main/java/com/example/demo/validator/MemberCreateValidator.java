package com.example.demo.validator;

import com.example.demo.common.CommonRegEx;
import com.example.demo.domain.MemberV1;
import com.example.demo.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MemberCreateValidator implements Validator {

    private final MemberService memberService;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(MemberV1.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (!this.supports(target.getClass())) {
            return;
        }

        MemberV1 memberV1 = (MemberV1) target;

        /**
         * id 중복 체크
         */
        String memberId = memberV1.getMemberId();
        Optional<MemberV1> memberFromDB = memberService.findMemberByMemberId(memberId);
        if (memberFromDB.isPresent()) {
            errors.rejectValue("memberId", "", "동일한 ID가 존재 합니다. 다른 ID를 사용해 주십시오.");
            return;
        }

        /**
         * id 체크
         * 최소 5자, 최대 20자
         * 영문소문자, 숫자만 가능
         */
        if (StringUtils.isBlank(memberId) || !memberId.matches(CommonRegEx.REG_EX_ID)) {
            errors.rejectValue("memberId", "", "회원 ID 형식이 잘못되었습니다.\n최소 5자, 최대 20자, 영문소문자 및 숫자만 가능합니다.");
            return;
        }

        /**
         * 비밀번호 체크
         * 최소 8자, 최대 20자
         * 영문대소문자, 숫자, 특수문자 2가지 이상 조합
         */
        String password = memberV1.getPassword();
        if (StringUtils.isBlank(password) || !password.matches(CommonRegEx.REG_EX_PASSWORD)) {
            errors.rejectValue("password", "", "비밀번호 형식이 잘못되었습니다.\n최소 8자, 최대 20자, 영문대소문자/숫자/특수문자 2가지 이상 조합이 필요합니다.");
            return;
        }

        /**
         * 이메일 형식 체크
         */
        String email = memberV1.getEmail();
        if (StringUtils.isBlank(email) || !email.matches(CommonRegEx.REG_EX_EMAIL)) {
            errors.rejectValue("email", "", "이메일 형식이 잘못되었습니다.");
            return;
        }

        /**
         * 휴대폰 번호 형식 체크
         */
        String cellNo = memberV1.getCellNo();
        if (StringUtils.isBlank(cellNo) || !cellNo.matches(CommonRegEx.REG_EX_CELL_NO)) {
            errors.rejectValue("cellNo", "", "휴대폰 형식이 잘못되었습니다.");
            return;
        }
    }
}
