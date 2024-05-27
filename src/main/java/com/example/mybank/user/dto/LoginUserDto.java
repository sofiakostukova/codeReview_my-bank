package com.example.mybank.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Schema(description = "Сущность на аутентификацию")
public class LoginUserDto {
    @Schema(description = "Логин")
    @NotBlank
    private String login;
    @Schema(description = "Пароль")
    @NotBlank
    private String password;
}