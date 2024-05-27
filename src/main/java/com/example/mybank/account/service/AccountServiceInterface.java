package com.example.mybank.account.service;

import com.example.mybank.account.dto.AccountDto;
import com.example.mybank.user.model.User;

import java.math.BigDecimal;
import java.util.List;

public interface AccountServiceInterface {
    AccountDto save(BigDecimal initBalance, User user);

    void updateBalances();

    List<AccountDto> transferMoney(long senderId, long recipientId, BigDecimal sum);
}