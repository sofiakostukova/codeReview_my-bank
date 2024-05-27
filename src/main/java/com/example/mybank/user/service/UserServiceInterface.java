package com.example.mybank.user.service;

import com.example.mybank.user.dto.*;
import com.example.mybank.user.model.User;
import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;
import java.util.List;

public interface UserServiceInterface {
    User save(RegisterUserDto dto);

    UserDto findById(Long id);

    UserDto updateEmailOrPhone(Long userId, UpdateUserRequest dto);

    void deleteEmailOrPhone(Long userId, DeleteUserRequest dto);

    List<UserDto> findUsers(String fullName, String email, String phone, LocalDateTime birthday,
        Integer from, Integer size, HttpServletRequest request);
}