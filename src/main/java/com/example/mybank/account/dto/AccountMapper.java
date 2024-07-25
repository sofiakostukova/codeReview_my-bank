package com.example.mybank.account.dto;

import com.example.mybank.account.model.Account;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class AccountMapper {
    @Transactional(readOnly = true) // Вполне вероятно, что в данном случае аннотация @Transactional не нужна, так как метод не выполняет никаких операций с базой данных. Она может быть добавлена, если вы будете выполнять операции, требующие транзакционного контекста.
    public static AccountDto toAccountDtoFromAccount(Account account) { // Отсутствует проверка на null. Если метод toAccountDtoFromAccount будет вызван с null, это приведет к NullPointerException, когда вы попытаетесь вызвать методы getInitBalance(), getBalance() или getUser() у account.
        return AccountDto.builder()
                .initBalance(account.getInitBalance())
                .balance(account.getBalance())
                .user(account.getUser())
                .build();
    }
}