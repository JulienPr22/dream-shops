package com.julienprr.dreamshops.service.user;

import com.julienprr.dreamshops.model.User;
import com.julienprr.dreamshops.request.UserCreateRequest;
import com.julienprr.dreamshops.request.UserUpdateRequest;

public interface IUserService {
    User getUserById(Long userId);
    User createUser(UserCreateRequest request);
    User updateUser(UserUpdateRequest request, Long userId);
    void deleteUser(Long userId);
}
