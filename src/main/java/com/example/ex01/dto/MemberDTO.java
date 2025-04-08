package com.example.ex01.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberDTO {
    private String username;
    private String password;
    private String role;
}
