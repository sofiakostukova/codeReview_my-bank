package com.example.mybank.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Сущность на регистрацию пользователя")
public class RegisterUserDto {
    @Schema(description = "ФИО")
    private String fullName;
    @Schema(description = "Уникальный логин")
    @NotBlank
    private String login;
    @Schema(description = "Пароль")
    @NotEmpty
    private String password;
    @Schema(description = "Изначальный баланс")
    @NotNull
    private BigDecimal initBalance;
    @Schema(description = "Номер телефона")
    @NotBlank
    private String phone;
    @Schema(description = "Эл.почта")
    @Email
    @NotBlank
    private String email;
}