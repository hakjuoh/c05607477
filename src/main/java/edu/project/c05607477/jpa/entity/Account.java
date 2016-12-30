package edu.project.c05607477.jpa.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;

@Entity
@Table(name = "ACCOUNTS")
public class Account implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "ACCOUNT_ID")
    private Long accountId;

    @Column(name = "ACCOUNT_NO")
    private String accountNo;

    @Column(name = "ACCOUNT_TYPE")
    private String accountType;

    @Column(name = "BALANCE")
    private String balance;

    @Column(name = "CREDIT_CARD")
    private String creditCard;

    @Column(name = "SORT_CODE")
    private Integer sortCode;

    @Column(name = "TRANSACTION_LIST")
    private String transactionList;

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(String creditCard) {
        this.creditCard = creditCard;
    }

    public Integer getSortCode() {
        return sortCode;
    }

    public void setSortCode(Integer sortCode) {
        this.sortCode = sortCode;
    }

    public String getTransactionList() {
        return transactionList;
    }

    public void setTransactionList(String transactionList) {
        this.transactionList = transactionList;
    }
}
