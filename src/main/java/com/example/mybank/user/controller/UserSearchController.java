package com.example.mybank.user.controller;

import com.example.mybank.user.dto.UserDto;
import com.example.mybank.user.service.UserServiceInterface;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Поиск пользователей")
public class UserSearchController {
    private final UserServiceInterface userService;

    @GetMapping("/{id}")
    @Operation(summary = "Поиск по идентификатору")
    @SecurityRequirement(name = "JWT")
    @ResponseStatus(HttpStatus.OK)
    public UserDto findById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @Operation(summary = "Поиск пользователей по фильтрам", description = "Искать можно любого клиента")
    @SecurityRequirement(name = "JWT")
    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> findUsersByCriteria(
        @RequestParam(required = false) @Parameter(description = "ФИО пользователя") String fullName,
        @RequestParam(required = false) @Parameter(description = "Адрес электронной почты") String email,
        @RequestParam(required = false) @Parameter(description = "Номер телефона") String phone,
        @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @Parameter(description = "Дата рождения") LocalDateTime birthday,
        @RequestParam(defaultValue = "0", required = false) @Parameter(description = "Количество элементов, которые нужно пропустить для формирования текущего набора") Integer from,
        @RequestParam(defaultValue = "10", required = false) @Parameter(description = "количество элементов в наборе") Integer size,
        HttpServletRequest request) {
        log.info("Получение пользователей с возможностью фильтрации");

        return userService.findUsers(fullName, email, phone, birthday, from, size, request);
    }
}