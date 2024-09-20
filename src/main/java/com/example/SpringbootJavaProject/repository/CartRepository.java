package com.example.SpringbootJavaProject.repository;


import com.example.SpringbootJavaProject.entitiy.Cart;
import com.example.SpringbootJavaProject.entitiy.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByMember(Member member);

    @Modifying
    @Query("delete from CartItem ci where ci.cart.id = :cartId")
    void clearCartItems(@Param("cartId") Long cartId);
}