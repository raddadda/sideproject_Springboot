package com.example.SpringbootJavaProject.entitiy;

import com.example.SpringbootJavaProject.Role;
import com.github.f4b6a3.uuid.UuidCreator;
import jakarta.persistence.*;
import lombok.*;


import java.util.UUID;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Member{
    @Id
    @Column(length=16)
    private UUID id;

    @Column
    private String loginId;
    @Column
    private String password;
    @Column
    private String name;
    @Column
    private String email;
    @Enumerated(EnumType.STRING)
    private Role role;
    @PrePersist
    public void createId(){
        this.id = UuidCreator.getTimeOrdered();
    }
    //provider : google이 들어감
    private String provider;
    // providerId : 구굴 로그인 한 유저의 고유 ID가 들어감
    private String providerId;


    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

}