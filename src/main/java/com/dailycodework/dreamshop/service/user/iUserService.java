package com.dailycodework.dreamshop.service.user;

import com.dailycodework.dreamshop.model.User;
import com.dailycodework.dreamshop.request.CreateUserRequest;
import com.dailycodework.dreamshop.request.UpdateUserRequest;
import com.dailycodework.dreamshop.dto.UserDto;

public interface iUserService {
    User getUserById(Long id);

    User createUser(CreateUserRequest user);

    User updateUser(Long id, UpdateUserRequest user);

    void deleteUser(Long id);

    UserDto convertUserToDto(User user);

    User getAuthenticatedUser();
}
