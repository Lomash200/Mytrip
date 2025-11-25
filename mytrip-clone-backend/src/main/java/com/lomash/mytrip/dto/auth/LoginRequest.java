package com.lomash.mytrip.dto.auth;


import lombok.Data;


@Data
public class LoginRequest {
    private String username; // or email
    private String password;
}