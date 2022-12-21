package com.example.demo.service;

import com.example.demo.domain.MemberV1;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.entity.Member;
import com.example.demo.util.EncryptionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final EncryptionUtil encryptionUtil;

    public boolean createMember(MemberV1 memberV1) {
        try {
            Member member = Member.builder()
                    .memId(memberV1.getMemberId())
                    .passwd(encryptionUtil.hashPassword(memberV1.getPassword()))
                    .email(encryptionUtil.encrypt(memberV1.getEmail()))
                    .cellNo(encryptionUtil.encrypt(memberV1.getCellNo()))
                    .build();
            return memberRepository.saveMember(member) > 0;
        } catch (Exception e) {
            log.error("회원등록 오류 {}", e.getMessage(), e);
            return false;
        }
    }

    public Optional<MemberV1> findMemberByMemberNo(long memberNo) {
        Optional<Member> member = memberRepository.findMembers(Member.builder()
                        .memNo(memberNo)
                        .build())
                .stream().findFirst();
        return this.getMemberV1From(member);
    }

    public Optional<MemberV1> findMemberByMemberId(String memberId) {
        Optional<Member> member = memberRepository.findMembers(Member.builder()
                        .memId(memberId)
                        .build())
                .stream().findFirst();
        return this.getMemberV1From(member);
    }

    public List<MemberV1> findAllMembers() {
        return memberRepository.findMembers(new Member()).stream()
                .map(m -> this.getMemberV1From(m))
                .collect(Collectors.toList());
    }

    private Optional<MemberV1> getMemberV1From(Optional<Member> member) {
        return member.map(m -> {
            try {
                return getMemberV1From(m);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private MemberV1 getMemberV1From(Member member) {
        try {
            return MemberV1.builder()
                    .memberNo(member.getMemNo())
                    .memberId(member.getMemId())
                    .password(member.getPasswd())
                    .email(encryptionUtil.decrypt(member.getEmail()))
                    .cellNo(encryptionUtil.decrypt(member.getCellNo()))
                    .build();
        } catch (Exception e) {
            log.error("회원정보 조회 오류 {}", e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
