package org.sid.ebankservice.web;

import lombok.RequiredArgsConstructor;
import org.sid.ebankservice.dto.BankAccountRequestDTO;
import org.sid.ebankservice.dto.BankAccountResponseDTO;
import org.sid.ebankservice.entities.BankAccount;
import org.sid.ebankservice.repositories.BankAccountRepository;
import org.sid.ebankservice.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AccountRestController {

    private final BankAccountRepository bankAccountRepository;
    private final AccountService accountService;

    // ✅ Récupérer tous les comptes
    @GetMapping("/bankAccounts")
    public ResponseEntity<List<BankAccount>> bankAccounts() {
        List<BankAccount> accounts = bankAccountRepository.findAll();
        return ResponseEntity.ok(accounts);
    }

    // ✅ Récupérer un compte par ID
    @GetMapping("/bankAccounts/{id}")
    public ResponseEntity<BankAccount> bankAccount(@PathVariable String id) {
        BankAccount account = bankAccountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account " + id + " not found"));
        return ResponseEntity.ok(account);
    }

    // ✅ Créer un compte bancaire
    @PostMapping("/bankAccounts")
    public ResponseEntity<BankAccountResponseDTO> save(@RequestBody BankAccountRequestDTO requestDTO) {
        try {
            BankAccountResponseDTO response = accountService.addAccount(requestDTO);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Log pour déboguer côté serveur
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    // ✅ Mettre à jour un compte
    @PutMapping("/bankAccounts/{id}")
    public ResponseEntity<BankAccount> update(@PathVariable String id, @RequestBody BankAccount bankAccount) {
        BankAccount existing = bankAccountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account " + id + " not found"));

        if (bankAccount.getBalance() != null) existing.setBalance(bankAccount.getBalance());
        if (bankAccount.getCreatedAt() != null) existing.setCreatedAt(bankAccount.getCreatedAt());
        if (bankAccount.getType() != null) existing.setType(bankAccount.getType());
        if (bankAccount.getCurrency() != null) existing.setCurrency(bankAccount.getCurrency());

        BankAccount updated = bankAccountRepository.save(existing);
        return ResponseEntity.ok(updated);
    }

    // ✅ Supprimer un compte
    @DeleteMapping("/bankAccounts/{id}")
    public ResponseEntity<Void> deleteBankAccount(@PathVariable String id) {
        if (!bankAccountRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        bankAccountRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
