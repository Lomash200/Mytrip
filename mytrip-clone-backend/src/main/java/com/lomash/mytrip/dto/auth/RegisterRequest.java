package com.lomash.mytrip.dto.auth;

import lombok.Data;

@Data
public class RegisterRequest {

    private String username;
    private String email;      // REQUIRED FIELD
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
}
