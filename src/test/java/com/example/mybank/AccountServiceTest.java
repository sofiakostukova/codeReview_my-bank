package com.example.mybank;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.example.mybank.account.dto.AccountDto;
import com.example.mybank.account.model.Account;
import com.example.mybank.account.repository.AccountRepository;
import com.example.mybank.account.service.AccountService;
import com.example.mybank.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.*;

@SpringBootTest
@ActiveProfiles("test")
public class AccountServiceTest {
    @MockBean
    private AccountRepository accountRepository;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void testTransferMoney() {
        long senderId = 1L;
        long recipientId = 2L;
        BigDecimal transferAmount = new BigDecimal("100.00");
        Account senderAccount = new Account();
        senderAccount.setId(senderId);
        senderAccount.setBalance(new BigDecimal("200.00"));
        Account recipientAccount = new Account();
        recipientAccount.setId(recipientId);
        recipientAccount.setBalance(new BigDecimal("50.00"));

        when(accountRepository.findById(senderId)).thenReturn(Optional.of(senderAccount));
        when(accountRepository.findById(recipientId)).thenReturn(Optional.of(recipientAccount));
        when(accountRepository.save(any(Account.class))).thenAnswer(invocation -> invocation.getArgument(0));

        AccountService accountService = new AccountService(accountRepository);
        List<AccountDto> result = accountService.transferMoney(senderId, recipientId, transferAmount);

        assertEquals(2, result.size());
        AccountDto senderDto = result.get(0);
        AccountDto recipientDto = result.get(1);
        assertEquals("100.00", senderDto.getBalance().toString());
        assertEquals("150.00", recipientDto.getBalance().toString());

        ArgumentCaptor<Account> accountCaptor = ArgumentCaptor.forClass(Account.class);
        verify(accountRepository, times(2)).save(accountCaptor.capture());
        List<Account> savedAccounts = accountCaptor.getAllValues();
        assertEquals(new BigDecimal("100.00"), savedAccounts.get(0).getBalance());
        assertEquals(new BigDecimal("150.00"), savedAccounts.get(1).getBalance());
    }
}