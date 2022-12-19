package com.example.demo.repository;

import com.example.demo.repository.entity.Member;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface MemberRepository {
    Optional<Member> findMember(Member member);
    List<Member> findAllMembers();
    int saveMember(Member member);
}
