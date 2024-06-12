package org.example.mybatissample.controller;

import lombok.RequiredArgsConstructor;
import org.example.mybatissample.model.Account;
import org.example.mybatissample.model.searchparams.AccountSearchParam;
import org.example.mybatissample.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    @GetMapping("")
    public ResponseEntity<?> getAccounts() {
        List<Account> accounts = accountService.findAll();
        return ResponseEntity.ok().body(accounts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAccount(@PathVariable Long id) {
        Account account = accountService.findById(id);
        return ResponseEntity.ok().body(account);
    }

    @GetMapping("/detail")
    public ResponseEntity<?> getAccountByUsername(@RequestParam AccountSearchParam accountSearchParam) {
        Account account = accountService.findByUsername(accountSearchParam.getUsername());
        return ResponseEntity.ok().body(account);
    }

    @PutMapping("")
    public ResponseEntity<?> createAccount(@RequestBody Account account) {
        int savedCount =  accountService.update(account);
        return ResponseEntity.ok().body(savedCount);
    }
}
