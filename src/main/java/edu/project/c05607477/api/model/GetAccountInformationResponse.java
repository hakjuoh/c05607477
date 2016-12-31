package edu.project.c05607477.api.model;

import edu.project.c05607477.jpa.entity.AccountType;

import java.math.BigInteger;

public class GetAccountInformationResponse {

    private String accountNo;

    private AccountType accountType;

    private BigInteger balance;

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public BigInteger getBalance() {
        return balance;
    }

    public void setBalance(BigInteger balance) {
        this.balance = balance;
    }
}
