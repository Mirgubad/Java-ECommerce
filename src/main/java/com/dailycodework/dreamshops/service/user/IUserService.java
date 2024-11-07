package com.dailycodework.dreamshops.service.user;

import com.dailycodework.dreamshops.dto.UserDto;
import com.dailycodework.dreamshops.model.User;
import com.dailycodework.dreamshops.request.RegisterRequest;
import com.dailycodework.dreamshops.request.UpdadteUserRequest;
import com.dailycodework.dreamshops.response.JwtResponse;

public interface IUserService {
    User getUserById(Long userId);
    JwtResponse registerUser(RegisterRequest request);
    User updateUser(UpdadteUserRequest user,Long userId);
    void deleteUser(Long userId);
    UserDto convertToDto(User user);
    User getAuthenticatedUser();
}
