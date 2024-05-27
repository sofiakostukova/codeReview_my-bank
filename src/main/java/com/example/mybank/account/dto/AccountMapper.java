package com.example.mybank.account.dto;

import com.example.mybank.account.model.Account;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class AccountMapper {
    @Transactional(readOnly = true)
    public static AccountDto toAccountDtoFromAccount(Account account) {
        return AccountDto.builder()
            .initBalance(account.getInitBalance())
            .balance(account.getBalance())
            .user(account.getUser())
            .build();
    }
}