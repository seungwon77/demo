package com.example.demo.validator;

import com.example.demo.common.CommonRegEx;
import com.example.demo.domain.MemberV1;
import com.example.demo.service.MemberService;
import com.example.demo.util.EncryptionUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MemberCreateValidator implements Validator {

    private final MemberService memberService;

    private final EncryptionUtil encryptionUtil;

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
        try {
            String encryptedMemberId = encryptionUtil.encrypt(memberV1.getMemberId());
            Optional<MemberV1> memberFromDB = memberService.findMemberByMemberId(encryptedMemberId);
            if (memberFromDB.isPresent()) {
                errors.rejectValue("memberId", "", "동일한 ID가 존재 합니다. 다른 ID를 사용해 주십시오.");
            }
        } catch (Exception e) {
            errors.rejectValue("memberId", "", "입력하신 ID 검증 과정에서 오류가 발생하였습니다. 오류가 반복되면 다른 ID를 사용해 주십시오.");
        }

        /**
         * id 체크
         * 최소 5자, 최대 20자
         * 영문소문자, 숫자만 가능
         */
        if (StringUtils.isBlank(memberId) || !memberId.matches(CommonRegEx.REG_EX_ID)) {
            errors.rejectValue("memberId", "", "회원 ID 형식이 잘못되었습니다.\n최소 5자, 최대 20자, 영문소문자 및 숫자만 가능합니다.");
        }

        /**
         * 비밀번호 체크
         * 최소 8자, 최대 20자
         * 영문대소문자, 숫자, 특수문자 2가지 이상 조합
         */
        String password = memberV1.getPassword();
        if (StringUtils.isBlank(password) || !password.matches(CommonRegEx.REG_EX_PASSWORD) || password.length() < 8 || password.length() > 20) {
            errors.rejectValue("password", "", "비밀번호 형식이 잘못되었습니다.\n최소 8자, 최대 20자, 영문대소문자/숫자/특수문자 2가지 이상 조합이 필요합니다.");
        }

        /**
         * 이메일 형식 체크
         */
        String email = memberV1.getEmail();
        if (StringUtils.isBlank(email) || !email.matches(CommonRegEx.REG_EX_EMAIL) || email.length() > 50) {
            errors.rejectValue("email", "", "이메일 형식이 잘못되었습니다. (최대 50자)");
        }

        /**
         * 휴대폰 번호 형식 체크
         */
        String cellNo = memberV1.getCellNo();
        if (StringUtils.isBlank(cellNo) || !cellNo.matches(CommonRegEx.REG_EX_CELL_NO)) {
            errors.rejectValue("cellNo", "", "휴대폰 형식이 잘못되었습니다. (010-0000-1111)");
        }
    }
}
