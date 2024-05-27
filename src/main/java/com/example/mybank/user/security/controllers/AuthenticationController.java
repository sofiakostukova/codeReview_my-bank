package com.example.mybank.user.security.controllers;

import com.example.mybank.user.dto.*;
import com.example.mybank.user.model.User;
import org.springframework.http.*;
import com.example.mybank.user.security.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Регистрация и аутентификация")
public class AuthenticationController {
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    @Operation(summary = "Регистрация", description = "Позволяет зарегистрировать пользователя")
    @PostMapping("/signup")
    public User register(@RequestBody @Valid RegisterUserDto registerUserDto) {
        log.info("Добавление нового пользователя");

        return authenticationService.signUp(registerUserDto);
    }

    @Operation(summary = "Аутентификация", description = "Позволяет проверить подлинность пользователя")
    @PostMapping("/login")
    public LoginResponse authenticate(@RequestBody @Valid LoginUserDto loginUserDto) {
        return authenticationService.authenticate(loginUserDto);
    }
}