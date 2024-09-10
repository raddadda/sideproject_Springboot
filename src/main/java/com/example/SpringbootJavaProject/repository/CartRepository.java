package com.example.SpringbootJavaProject.repository;


import com.example.SpringbootJavaProject.entitiy.Cart;
import com.example.SpringbootJavaProject.entitiy.Member;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByMember(Member member);

}