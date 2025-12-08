package com.lomash.mytrip.dto;

import lombok.*;
import java.util.List; // List import added

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private boolean enabled;

    // 👇 Ye naya field add kiya hai
    private List<String> roles;
}