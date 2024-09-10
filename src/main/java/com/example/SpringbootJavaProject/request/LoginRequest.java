package com.example.SpringbootJavaProject.request;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
public class LoginRequest {
    private String loginId;
    private String password;

//    public Member toEntity(){
//        return Member.builder()
//                .loginId(this.loginId)
//                .password(this.password)
//                .build();
//    }
}
