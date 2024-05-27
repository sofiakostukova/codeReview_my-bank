package com.example.mybank.account.dto;

import com.example.mybank.user.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Сущность аккаунта")
public class AccountDto {
    @JsonIgnore
    @Schema(description = "Владелец")
    private User user;
    @Schema(description = "Изначальный баланс")
    private BigDecimal initBalance;
    @Schema(description = "Баланс")
    private BigDecimal balance;
}