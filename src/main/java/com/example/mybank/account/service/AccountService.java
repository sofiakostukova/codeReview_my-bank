package com.example.mybank.account.service;

import com.example.mybank.account.dto.AccountDto;
import com.example.mybank.account.model.Account;
import com.example.mybank.account.repository.AccountRepository;
import com.example.mybank.core.exception.exceptions.*;
import com.example.mybank.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static com.example.mybank.account.dto.AccountMapper.toAccountDtoFromAccount;

@Service
@RequiredArgsConstructor
public class AccountService implements AccountServiceInterface {
    private final AccountRepository accountRepository;

    @Override
    @Transactional
    public AccountDto save(BigDecimal initBalance, User user) {
        Account account = new Account();

        account.setUser(user);
        account.setInitBalance(initBalance);
        account.setBalance(initBalance);

        return toAccountDtoFromAccount(accountRepository.save(account));
    }

    @Override
    @Transactional
    @Scheduled(fixedRate = 60000)
    public void updateBalances() {
        List<Account> accounts = accountRepository.findAll();

        for (Account account : accounts) {
            BigDecimal currentBalance = account.getBalance();
            BigDecimal maxBalance = account.getInitBalance().multiply(BigDecimal.valueOf(2.07));
            BigDecimal updatedBalance = currentBalance.multiply(BigDecimal.valueOf(1.05));

            if (updatedBalance.compareTo(maxBalance) > 0) {
                updatedBalance = maxBalance;
            }

            account.setBalance(updatedBalance);
            accountRepository.save(account);
        }
    }

    @Override
    @Transactional
    public synchronized List<AccountDto> transferMoney(long senderId, long recipientId, BigDecimal sum) {
        validateTransferSum(sum);
        Account senderAccount = getExistingAccount(senderId);
        Account recipientAccount = getExistingAccount(recipientId);

        validateSenderSumEnough(senderAccount.getBalance(), sum);

        senderAccount.setBalance(senderAccount.getBalance().subtract(sum));
        recipientAccount.setBalance(recipientAccount.getBalance().add(sum));
        senderAccount = accountRepository.save(senderAccount);
        recipientAccount = accountRepository.save(recipientAccount);

        AccountDto senderDto = toAccountDtoFromAccount(senderAccount);
        AccountDto recipientDto = toAccountDtoFromAccount(recipientAccount);
        return List.of(senderDto, recipientDto);
    }

    public Account getExistingAccount(long userId) {
        return accountRepository.findById(userId).orElseThrow(
            () -> new AccountNotFoundException("Аккаунт с id " + userId + " не найден.")
        );
    }

    private void validateTransferSum(BigDecimal sum) {
        if (sum.compareTo(BigDecimal.ZERO) <= 0) {
            throw new AccountBadRequestException("Сумма перевода должна быть больше 0.");
        }
    }

    private void validateSenderSumEnough(BigDecimal senderBalance, BigDecimal sum) {
        if (senderBalance.compareTo(sum) < 0) {
            throw new AccountBadRequestException("На счету отправителя недостаточно денежных средств.");
        }
    }
}