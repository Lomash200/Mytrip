package com.lomash.mytrip.service;


import com.lomash.mytrip.dto.auth.LoginRequest;
import com.lomash.mytrip.dto.auth.RegisterRequest;
import com.lomash.mytrip.dto.auth.LoginResponse;
import com.lomash.mytrip.entity.User;


public interface AuthService {
    LoginResponse login(LoginRequest request);
    User getLoggedUser();
    void register(RegisterRequest request);
}