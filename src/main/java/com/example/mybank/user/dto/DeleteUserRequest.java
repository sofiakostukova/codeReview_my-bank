package com.example.mybank.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Schema(description = "Сущность на удаление данных пользователя")
public class DeleteUserRequest {
    @Schema(description = "Номер телефона на удаление")
    private String phone;
    @Schema(description = "Адрес эл.почты на удаление")
    @Email
    private String email;
}