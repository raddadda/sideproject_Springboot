package com.example.SpringbootJavaProject.request;

import com.example.SpringbootJavaProject.Role;
import com.example.SpringbootJavaProject.entitiy.Member;
import lombok.Data;


@Data
public class JoinRequest {
    private String loginId;
    private String password;
    private String name;
    private String email;

    public Member toEntity(){
        return Member.builder()
                .loginId(this.loginId)
                .password(this.password)
                .name(this.name)
                .email(this.email)
                .role(Role.USER)
                .build();
    }
}
