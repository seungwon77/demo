package com.example.demo.repository;

import com.example.demo.repository.entity.Member;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberRepository {
    List<Member> findMembers(Member member);
    int saveMember(Member member);
}
