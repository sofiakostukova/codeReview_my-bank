package com.example.mybank.account.controller;

import com.example.mybank.account.dto.AccountDto;
import com.example.mybank.account.service.AccountServiceInterface;
import com.example.mybank.user.model.User;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Действия со счетами аккаунтов")
public class AccountController {
    private final AccountServiceInterface accountService;

    @Operation(summary = "Обновление балансов",
        description = "Раз в минуту баланс каждого клиента увеличивается на 5% но не более 207% от начального депозита")
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void updateBalances() {
        log.info("Обновление баланса всех счетов.");

        accountService.updateBalances();
    }

    @Operation(summary = "Перевод денег с одного счета на другой",
        description = "Со счета аутентифицированного пользователя, на счёт другого пользователя")
    @SecurityRequirement(name = "JWT")
    @PutMapping("/transfer")
    @ResponseStatus(HttpStatus.OK)
    public List<AccountDto> transferMoney(
        @RequestParam @Parameter(description = "Индентификатор получателя") Long recipientId,
        @RequestParam @Parameter(description = "Сумма перевода") BigDecimal sum
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long senderId = ((User) authentication.getPrincipal()).getId();

        log.info("Перевод денег в количестве " + sum + " от пользователя с id " + senderId +
            " пользователю с id " + recipientId);

        return accountService.transferMoney(senderId, recipientId, sum);
    }
}