package com.example.SpringbootJavaProject.controller;

import com.example.SpringbootJavaProject.entitiy.Member;
import com.example.SpringbootJavaProject.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/login")
public class MemberController {
    private final MemberService memberService;
    //생성자 주입 방법은?
    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/mypage")
    public String joinForm(Model model){
        model.addAttribute("infoRequest", "마이페이지");

        // 현재 인증된 사용자의 로그인 ID 가져오기
        String loginId = SecurityContextHolder.getContext().getAuthentication().getName();
        // 로그인한 사용자 정보 가져오기
        Member loginMember = memberService.getMemberInfoByLoginId(loginId);

        // 사용자가 존재하는 경우, 모델에 사용자 정보 추가
        if (loginMember != null) {
            model.addAttribute("member", loginMember);
        } else {
            // 로그인한 사용자가 존재하지 않을 경우 처리
            return "redirect:/login";
        }

        return "/members/mypage";
    }



}
