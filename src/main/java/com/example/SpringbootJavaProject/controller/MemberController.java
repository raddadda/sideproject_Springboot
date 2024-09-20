package com.example.SpringbootJavaProject.controller;

import com.example.SpringbootJavaProject.entitiy.Member;
import com.example.SpringbootJavaProject.request.MemberUpdateRequest;
import com.example.SpringbootJavaProject.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;


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


    // 회원 정보 수정 페이지로 이동
    @GetMapping("/mypage/edit")
    public String editMemberForm(Model model) {
        String loginId = SecurityContextHolder.getContext().getAuthentication().getName();
        Member member = memberService.getMemberInfoByLoginId(loginId);

        if (member != null) {
            model.addAttribute("member", member);  // 회원 정보를 모델에 추가
            return "/members/editForm";  // 수정 폼으로 이동
        }

        return "redirect:/login";
    }
    // 수정된 정보를 저장

    @PostMapping("/mypage/edit")
    public String updateMemberInfo(@ModelAttribute("memberUpdateRequest") MemberUpdateRequest memberUpdateRequest, Principal principal) {
        String loginId = principal.getName();
        Member member = memberService.getMemberInfoByLoginId(loginId);

        if (member != null) {
            memberService.updateMemberInfo(memberUpdateRequest, member);
            return "redirect:/login/mypage";
        }

        return "redirect:/login";
    }

}
