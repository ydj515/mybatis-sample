package org.example.mybatissample.model;

import lombok.*;
import org.example.mybatissample.model.enums.AccountType;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account implements Serializable {

    @Serial
    private static final long serialVersionUID = 179338683734956806L;

    private Long id;
    private String userId;
    private String password;
    private String name;
    private String email;
    private LocalDateTime lastLoginAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
    private LocalDateTime lastPasswordUpdatedAt;
    private AccountType accountType;
    private int trialCount;
    private Set<Role> roles;


    @Builder
    public Account(String userId, String password, String name, String email, AccountType accountType) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
        this.accountType = accountType;
    }
}

