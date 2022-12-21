package com.example.demo.repository;

import com.example.demo.repository.entity.Member;
import com.example.demo.util.EncryptionUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    private EncryptionUtil encryptionUtil;

    @BeforeEach
    void setup() {
        encryptionUtil = new EncryptionUtil();
        ReflectionTestUtils.setField(encryptionUtil, "encryptionKey", "member-mushinsa0");
        ReflectionTestUtils.setField(encryptionUtil, "encryptionIV", "member-mushinsa0");
        ReflectionTestUtils.invokeMethod(encryptionUtil, "init");
    }

    @Test
    void findMembers() {
        Member member = Member.builder().memNo(1).build();
        List<Member> members = memberRepository.findMembers(member);
        Assertions.assertEquals(1, members.size());
        Assertions.assertEquals(1, members.get(0).getMemNo());

        List<Member> allMembers = memberRepository.findMembers(new Member());
        Assertions.assertEquals(5, allMembers.size());
        Assertions.assertEquals(5, allMembers.get(0).getMemNo());
    }

    @Test
    void createMember() throws Exception {
        Member member = this.getMember(6);
        int result = memberRepository.saveMember(member);
        Assertions.assertEquals(1, result);
    }

    private Member getMember(long memNo) throws Exception {
        return Member.builder()
                .memNo(memNo)
                .memId("test00" + memNo)
                .passwd(encryptionUtil.hashPassword("password1!"))
                .cellNo(encryptionUtil.encrypt("010-1234-4321"))
                .email(encryptionUtil.encrypt("test@test.com"))
                .build();
    }
}

