package com.example.SpringbootJavaProject.controller;

import com.example.SpringbootJavaProject.entitiy.Member;
import com.example.SpringbootJavaProject.request.JoinRequest;
import com.example.SpringbootJavaProject.request.LoginRequest;
import com.example.SpringbootJavaProject.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Iterator;

@Controller
@RequestMapping("/login")
public class SecurityLoginController {

    private final MemberService memberService;
    @Autowired
    public SecurityLoginController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping(value = {"", "/"})
    public String home(Model model) {

        model.addAttribute("pageName", "일반 로그인");

        String loginId = SecurityContextHolder.getContext().getAuthentication().getName();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iter = authorities.iterator();
        GrantedAuthority auth = iter.next();
        String role = auth.getAuthority();

        Member loginMember = memberService.getMemberInfoByLoginId(loginId);

        if (loginMember != null) {
            model.addAttribute("name", loginMember.getName());
        }

        return "home";
    }
    @GetMapping("/login")
    public String joinForm(Model model){
        model.addAttribute("pageName", "일반 로그인");
        model.addAttribute("loginRequest", new LoginRequest());
        return "/members/login";
    }

    @GetMapping("/join")
    public String createForm(Model model){
        model.addAttribute("pageName", "일반 로그인");

        // 회원가입을 위해서 model 통해서 joinRequest 전달
        model.addAttribute("joinRequest", new JoinRequest());
        return "/members/join";
    }

    @PostMapping("/join")
    public String create(@ModelAttribute JoinRequest joinRequest,
                         BindingResult bindingResult, Model model) {
        model.addAttribute("pageName", "일반 로그인");

        // 회원가입 시 홈 화면으로 이동
        memberService.join(joinRequest);

        return "redirect:/login";
    }
//@PostMapping("/join")
//public ResponseEntity<String> create(@RequestBody JoinRequest joinRequest) {
//    if (joinRequest.getPassword() == null || joinRequest.getPassword().isEmpty()) {
//        return ResponseEntity.badRequest().body("비밀번호는 필수입니다.");
//    }
//
//    memberService.join(joinRequest);
//    return ResponseEntity.ok("회원가입이 완료되었습니다.");
//}

//    @GetMapping("/logout")
//    public String logout(HttpServletRequest request, HttpServletResponse response) {
//        HttpSession session = request.getSession(false);
//        if (session != null) {
//            session.invalidate();
//        }
//        return "redirect:/"; // 홈 화면으로 리디렉션
//    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return ResponseEntity.ok("로그아웃 되었습니다.");
    }
}
