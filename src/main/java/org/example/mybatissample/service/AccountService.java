package org.example.mybatissample.service;

import lombok.RequiredArgsConstructor;
import org.example.mybatissample.mapper.AccountMapper;
import org.example.mybatissample.model.Account;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountMapper accountMapper;

    public List<Account> findAll() {
        return accountMapper.findAll();
    }

    public Account findById(Long id) {
        return accountMapper.findById(id);
    }

    public Account findByUsername(String username) {
        return accountMapper.findByUsername(username);
    }

    public int save(Account account) {
        return accountMapper.save(account);
    }

    public int update(Account account) {
        return accountMapper.update(account);
    }

    public int delete(Long id) {
        return accountMapper.delete(id);
    }

}
