package org.sid.ebankservice.service;

import org.sid.ebankservice.dto.BankAccountRequestDTO;
import org.sid.ebankservice.dto.BankAccountResponseDTO;
import org.sid.ebankservice.entities.BankAccount;
import org.sid.ebankservice.enums.AccountType;
import org.sid.ebankservice.mapper.AccountMapper;
import org.sid.ebankservice.repositories.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import java.util.Date;
import java.util.UUID;

/// //QUESTION ICI //// POURQUOI SPRING ET PAS JAKARTA
@Service
@Transactional

public class AccountServiceImpl implements AccountService {
    private final RestClient.Builder builder;
    @Autowired
    private BankAccountRepository bankAccountRepository;
    @Autowired
    private AccountMapper accountMapper;

    public AccountServiceImpl(RestClient.Builder builder) {
        this.builder = builder;
    }

    @Override
    public BankAccountResponseDTO addAccount(BankAccountRequestDTO bankAccountDto) {
        BankAccount bankAccount = BankAccount.builder()
                .id(UUID.randomUUID().toString())
                .type(bankAccountDto.getType())
                .balance(bankAccountDto.getBalance())
                .createdAt(new Date())
                .currency(bankAccountDto.getCurrency())
                .build();
        BankAccount savedBankAccount=bankAccountRepository.save(bankAccount);
        BankAccountResponseDTO bankAccountResponseDTO= accountMapper.fromBankAccount(savedBankAccount);

        return bankAccountResponseDTO;
    }
}
