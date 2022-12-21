package com.example.demo.service;

import com.example.demo.domain.MemberV1;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.entity.Member;
import com.example.demo.util.EncryptionUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {
    @InjectMocks
    private MemberService memberService;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private EncryptionUtil encryptionUtil;

    @Test
    void createMember() {
        MemberV1 memberV1 = this.getMemberV1();
        doReturn(1).when(memberRepository)
                .saveMember(any(Member.class));

        boolean isSuccess = memberService.createMember(memberV1);
        Assertions.assertTrue(isSuccess);
    }

    @Test
    void findMemberByMemberNo() {
        doReturn(this.getMemberList(1)).when(memberRepository)
                .findMembers(any(Member.class));
        Optional<MemberV1> memberV1 = memberService.findMemberByMemberNo(1l);
        Assertions.assertTrue(memberV1.isPresent());
        Assertions.assertEquals(1, memberV1.get().getMemberNo());
    }

    @Test
    void findMemberByMemberId() {
        doReturn(this.getMemberList(1)).when(memberRepository)
                .findMembers(any(Member.class));
        Optional<MemberV1> memberV1 = memberService.findMemberByMemberId("test001");
        Assertions.assertTrue(memberV1.isPresent());
        Assertions.assertEquals("test001", memberV1.get().getMemberId());
    }

    @Test
    void findAllMembers() {
        doReturn(this.getMemberList(5)).when(memberRepository)
                .findMembers(any(Member.class));
        List<MemberV1> allMembers = memberService.findAllMembers();
        Assertions.assertFalse(CollectionUtils.isEmpty(allMembers));
        Assertions.assertEquals(5, allMembers.size());
    }

    private MemberV1 getMemberV1() {
        return MemberV1.builder()
                .memberId("test001")
                .password("password1!")
                .email("test@test.com")
                .cellNo("010-1234-4321")
                .build();
    }

    private Member getMember(long memNo) {
        return Member.builder()
                .memNo(memNo)
                .memId("test00" + memNo)
                .passwd(encryptionUtil.hashPassword("password1!"))
                .cellNo("010-1234-4321")
                .email("test@test.com")
                .build();
    }

    private List<Member> getMemberList(int size) {
        List<Member> list = new ArrayList<>();
        for (int i = 1; i <= size; i++) {
            list.add(this.getMember(i));
        }
        return list;
    }
}

