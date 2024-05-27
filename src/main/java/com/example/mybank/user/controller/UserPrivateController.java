package com.example.mybank.user.controller;

import com.example.mybank.user.dto.*;
import com.example.mybank.user.service.UserServiceInterface;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Редактирование данных пользователя")
public class UserPrivateController {
    private final UserServiceInterface userService;

    @Operation(summary = "Обновление адреса почты/телефона",
        description = "Позволяет обновить/добавить адрес почты и/или телефон")
    @SecurityRequirement(name = "JWT")
    @PatchMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto updateEmailOrPhone(
        @PathVariable @Parameter(description = "Идентификатор пользователя") Long userId,
        @RequestBody @Valid UpdateUserRequest dto
    ) {
        log.info("Редактирование данных пользователя(телефон/почта)");

        return userService.updateEmailOrPhone(userId, dto);
    }

    @Operation(summary = "Удаление адреса почты/телефона",
        description = "Удалить последний адрес почты и/или телефон нельзя")
    @SecurityRequirement(name = "JWT")
    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmailOrPhone(
        @PathVariable @Parameter(description = "Идентификатор пользователя") Long userId,
        @RequestBody @Valid DeleteUserRequest dto
    ) {
        log.info("Удаление электронной почты и/или телефона пользователя с id " + userId);

        userService.deleteEmailOrPhone(userId, dto);
    }
}