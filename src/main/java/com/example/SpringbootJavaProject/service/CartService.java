package com.example.SpringbootJavaProject.service;

import com.example.SpringbootJavaProject.entitiy.Cart;
import com.example.SpringbootJavaProject.entitiy.CartItem;
import com.example.SpringbootJavaProject.entitiy.Member;
import com.example.SpringbootJavaProject.entitiy.Product;
import com.example.SpringbootJavaProject.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final MemberService memberService;
    private final ProductService productService;
    // 장바구니에 아이템 추가
    public void addToCart(Member member, int quantity, Product product) {
        Cart cart = member.getCart();
        if (cart == null) {
            cart = new Cart();
            member.setCart(cart);
        }

        CartItem item = new CartItem();
        item.setProduct(product);
        item.setQuantity(quantity);

        cart.addItem(item);
        cartRepository.save(cart);


    }
    public Cart getCartByMember(Member member) {
        return member.getCart();
    }

    // 장바구니 조회
    public Cart getCartForUser(String username) {
        Member member = memberService.findByLoginId(username);
        return cartRepository.findByMember(member)
                .orElseThrow(() -> new IllegalArgumentException("장바구니가 존재하지 않습니다."));
    }

    // 회원에 따른 장바구니를 찾는 메서드 추가
    public Cart findByMember(Member member) {
        return cartRepository.findByMember(member)
                .orElseThrow(() -> new IllegalArgumentException("장바구니를 찾을 수 없습니다."));
    }
}

