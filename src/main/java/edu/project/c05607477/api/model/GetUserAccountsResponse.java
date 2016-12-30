package edu.project.c05607477.api.model;

import edu.project.c05607477.jpa.entity.AccountType;

import java.math.BigInteger;
import java.util.Collection;

public class GetUserAccountsResponse {

    public static class BriefUserAccount {

        private Long accountId;

        private String accountNo;

        private AccountType accountType;

        private BigInteger balance;

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

    private Collection<BriefUserAccount> userAccounts;

    public Collection<BriefUserAccount> getUserAccounts() {
        return userAccounts;
    }

    public void setUserAccounts(Collection<BriefUserAccount> userAccounts) {
        this.userAccounts = userAccounts;
    }
}
