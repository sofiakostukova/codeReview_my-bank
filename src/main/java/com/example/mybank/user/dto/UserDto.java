package com.example.mybank.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Сущность пользователя")
public class UserDto {
    @Schema(description = "Идентификатор пользователя")
    private Long id;
    @Schema(description = "ФИО")
    private String fullName;
    @Schema(description = "Уникальный логин")
    private String login;
    @Schema(description = "Номер/а телефона пользователя")
    private Set<String> phones;
    @Schema(description = "Адрес/а эл.почты пользователя")
    private Set<String> emails;
    @Schema(description = "Дата рождения")
    private LocalDateTime birthday;
}