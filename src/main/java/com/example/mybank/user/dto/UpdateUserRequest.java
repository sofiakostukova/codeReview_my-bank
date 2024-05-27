package com.example.mybank.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Schema(description = "Сущность на обновление данных пользователя")
public class UpdateUserRequest {
    @Schema(description = "Номер телефона на обновление/добавление")
    private String phone;
    @Schema(description = "Эл.почта на обновление/добавление")
    @Email
    private String email;
}