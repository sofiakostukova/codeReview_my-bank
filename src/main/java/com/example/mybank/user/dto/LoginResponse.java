package com.example.mybank.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Сущность-ответ с токеном доступа")
public class LoginResponse {
    @Schema(description = "Токен")
    private String token;
    @Schema(description = "Время, через которое токен станет недействительным")
    private long expiresIn;
}