package com.example.mybank.user.dto;

import com.example.mybank.user.model.User;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class UserMapper {
    public static UserDto toUserDtoFromUser(User user) {
        return UserDto.builder()
            .id(user.getId())
            .fullName(user.getFullName())
            .birthday(user.getBirthday())
            .emails(user.getEmails())
            .login(user.getLogin())
            .phones(user.getPhones())
            .build();
    }

    public static User toUserFromRequest(RegisterUserDto dto) {
        return User.builder()
            .login(dto.getLogin())
            .phones(new HashSet<>(Collections.singleton(dto.getPhone())))
            .emails(new HashSet<>(Collections.singleton(dto.getEmail())))
            .build();
    }
}
