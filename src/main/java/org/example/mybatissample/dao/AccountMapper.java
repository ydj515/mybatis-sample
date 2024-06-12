package org.example.mybatissample.dao;

import org.apache.ibatis.annotations.Mapper;
import org.example.mybatissample.model.Account;

import java.util.List;

@Mapper
public interface AccountMapper {
    List<Account> findAll();

    Account findById(Long id);

    Account findByUsername(String username);

    int save(Account account);

    int update(Account account);

    int delete(Long id);

}
