package org.sid.ebankservice.service;

import org.sid.ebankservice.dto.BankAccountRequestDTO;
import org.sid.ebankservice.dto.BankAccountResponseDTO;
import org.sid.ebankservice.entities.BankAccount;

public interface AccountService {
    public BankAccountResponseDTO addAccount(BankAccountRequestDTO bankAccountDto);

}
