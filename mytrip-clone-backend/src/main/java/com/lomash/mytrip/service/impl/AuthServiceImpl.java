package com.lomash.mytrip.service.impl;

import com.lomash.mytrip.config.JwtUtils;
import com.lomash.mytrip.dto.auth.LoginRequest;
import com.lomash.mytrip.dto.auth.LoginResponse;
import com.lomash.mytrip.dto.auth.RegisterRequest;
import com.lomash.mytrip.entity.Role;
import com.lomash.mytrip.entity.User;
import com.lomash.mytrip.exception.ApiException;
import com.lomash.mytrip.repository.RoleRepository;
import com.lomash.mytrip.repository.UserRepository;
import com.lomash.mytrip.service.AuthService;
import com.lomash.mytrip.service.EmailEventsService;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final EmailEventsService emailEventsService;

    public AuthServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder,
                           JwtUtils jwtUtils,
                           AuthenticationManager authenticationManager,
                           EmailEventsService emailEventsService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
        this.emailEventsService = emailEventsService;
    }

    // ========================= LOGIN ===========================
    @Override
    @Transactional
    public LoginResponse login(LoginRequest request) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );

            String token = jwtUtils.generateToken(request.getUsername());
            return new LoginResponse(token, "Bearer");

        } catch (AuthenticationException e) {
            throw new ApiException("Invalid username or password");
        }
    }

    // ======================== REGISTER ==========================
    @Override
    @Transactional
    public void register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ApiException("Email already in use");
        }

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new ApiException("Username already in use");
        }

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .enabled(true)
                .createdAt(Instant.now())
                .build();

        Role role = roleRepository.findByName("ROLE_USER")
                .orElseGet(() -> {
                    Role r = new Role();
                    r.setName("ROLE_USER");
                    return roleRepository.save(r);
                });

        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);

        userRepository.save(user);

        // Send welcome email (if EmailEventsService implemented)
       // emailEventsService.sendWelcomeEmail(user.getEmail(), user.getFirstName());
    }

    // ===================== GET LOGGED USER =====================
    @Override
    @Transactional
    public User getLoggedUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ApiException("User not found with username: " + username));
    }
}
