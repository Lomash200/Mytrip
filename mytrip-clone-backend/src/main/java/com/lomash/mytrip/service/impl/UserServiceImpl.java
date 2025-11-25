package com.lomash.mytrip.service.impl;

import com.lomash.mytrip.dto.UpdateUserRequest;
import com.lomash.mytrip.dto.UserDto;
import com.lomash.mytrip.entity.User;
import com.lomash.mytrip.exception.ApiException;
import com.lomash.mytrip.repository.UserRepository;
import com.lomash.mytrip.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private User getAuthenticatedUserEntity() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || auth.getName() == null) {
            throw new ApiException("Unauthenticated");
        }

        String usernameOrEmail = auth.getName();

        return userRepository.findByUsername(usernameOrEmail)
                .or(() -> userRepository.findByEmail(usernameOrEmail))
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + usernameOrEmail));
    }

    private UserDto mapToDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phone(user.getPhone())
                .enabled(user.isEnabled())
                .build();
    }

    @Override
    public UserDto getCurrentUser() {
        return mapToDto(getAuthenticatedUserEntity());
    }

    @Override
    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ApiException("User not found with id: " + id));
        return mapToDto(user);
    }

    @Override
    @Transactional
    public UserDto updateCurrentUser(UpdateUserRequest request) {
        User user = getAuthenticatedUserEntity();

        if (request.getFirstName() != null) user.setFirstName(request.getFirstName());
        if (request.getLastName() != null) user.setLastName(request.getLastName());
        if (request.getPhone() != null) user.setPhone(request.getPhone());

        if (request.getEmail() != null && !request.getEmail().equals(user.getEmail())) {
            if (userRepository.existsByEmail(request.getEmail())) {
                throw new ApiException("Email already in use");
            }
            user.setEmail(request.getEmail());
        }

        user.setUpdatedAt(Instant.now());
        return mapToDto(userRepository.save(user));
    }
}
