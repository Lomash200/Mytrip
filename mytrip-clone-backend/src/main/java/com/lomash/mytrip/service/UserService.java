package com.lomash.mytrip.service;

import com.lomash.mytrip.dto.UpdateUserRequest;
import com.lomash.mytrip.dto.UserDto;

public interface UserService {
    UserDto getCurrentUser();
    UserDto getUserById(Long id);
    UserDto updateCurrentUser(UpdateUserRequest request);
}
