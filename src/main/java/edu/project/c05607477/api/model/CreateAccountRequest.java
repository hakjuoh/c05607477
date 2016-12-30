package edu.project.c05607477.api.model;

import edu.project.c05607477.jpa.entity.AccountType;

public class CreateAccountRequest {

    private Long userId;

    private AccountType accountType;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }
}
