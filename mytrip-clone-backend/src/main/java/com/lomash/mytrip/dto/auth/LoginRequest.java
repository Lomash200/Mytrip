package com.lomash.mytrip.dto.auth;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

    @NotBlank(message = "Username or email is required")
    @JsonAlias({"username", "email", "usernameOrEmail"})
    private String usernameOrEmail;

    @NotBlank(message = "Password is required")
    private String password;
}
