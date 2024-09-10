package com.example.SpringbootJavaProject.controller;

import com.example.SpringbootJavaProject.entitiy.Cart;
import com.example.SpringbootJavaProject.entitiy.Member;
import com.example.SpringbootJavaProject.service.CartService;
import com.example.SpringbootJavaProject.service.MemberService;
import com.example.SpringbootJavaProject.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.security.Principal;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;
    private final MemberService memberService;
    private final ProductService productService;

    public CartController(CartService cartService, MemberService memberService, ProductService productService) {
        this.cartService = cartService;
        this.memberService = memberService;
        this.productService = productService;
    }



    // 장바구니 목록을 보여주는 메서드
    @GetMapping
    public String viewCart(Model model, Principal principal) {
        // 로그인한 사용자 이름으로 장바구니 조회
        Member member = memberService.findByLoginId(principal.getName());
        Cart cart = cartService.getCartByMember(member);
        model.addAttribute("cart", cart);
        return "cartView";
    }


}

