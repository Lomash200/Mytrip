package com.lomash.mytrip.mapper;

import com.lomash.mytrip.dto.UserDto;
import com.lomash.mytrip.entity.User;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public UserDto toDto(User user) {
        if (user == null) return null;

        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phone(user.getPhone())
                .enabled(user.isEnabled())
                // 👇 Roles ko String List me convert kar rahe hain
                .roles(user.getRoles().stream()
                        .map(role -> role.getName()) // e.g., "ROLE_USER"
                        .collect(Collectors.toList()))
                .build();
    }
}