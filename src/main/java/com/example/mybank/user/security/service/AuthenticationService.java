package com.example.mybank.user.security.service;

import com.example.mybank.user.dto.*;
import com.example.mybank.user.model.User;
import com.example.mybank.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public User signUp(RegisterUserDto dto) {
        return userService.save(dto);
    }

    public LoginResponse authenticate(LoginUserDto input) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                input.getLogin(),
                input.getPassword()
            )
        );

        var user = userService.userDetailsService().loadUserByUsername(input.getLogin());
        var jwt = jwtService.generateToken(user);

        return new LoginResponse(jwt, jwtService.getExpirationTime());
    }
}