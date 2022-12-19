package com.example.demo.controller;

import com.example.demo.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/member/home")
    public String searchMember(Model model) {
        model.addAttribute("members", memberService.findAllMembers());
        return "index";
    }

    @GetMapping("/member/search")
    public String searchMember(@RequestParam(value = "memberNo") Long memberNo, Model model) {
        model.addAttribute("member", memberService.findMemberByMemberNo(memberNo).orElse(null));
        return "search";
    }

    @GetMapping("/member/register")
    public String registerMemberForm(Model model) {
        return "register";
    }
}
