package com.example.demo.controller;

import com.example.demo.domain.MemberV1;
import com.example.demo.service.MemberService;
import com.example.demo.validator.MemberCreateValidator;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberCreateValidator memberCreateValidator;

    private final MemberService memberService;

    @GetMapping("/member/home")
    public String searchMember(Model model) {
        model.addAttribute("members", memberService.findAllMembers());
        return "index";
    }

    @GetMapping("/member/search")
    public String searchMember(@RequestParam(value = "memberNo") Long memberNo, Model model) {
        Optional<MemberV1> member = memberService.findMemberByMemberNo(memberNo);
        model.addAttribute("members", member.map(m -> Lists.newArrayList(m)).orElse(Lists.newArrayList()));
        return "search";
    }

    @GetMapping("/member/register")
    public String registerMemberForm(Model model) {
        model.addAttribute("memberV1", new MemberV1());
        return "register";
    }

    @PostMapping("/member/register")
    public String registerMember(MemberV1 memberV1, BindingResult errors, Model model) {
        memberCreateValidator.validate(memberV1, errors);
        if (errors.hasErrors()) {
            return "register";
        }
        memberService.createMember(memberV1);
        return "redirect:/member/home";
    }
}
