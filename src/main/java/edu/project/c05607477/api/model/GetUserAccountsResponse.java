package edu.project.c05607477.api.model;

import java.util.Collection;

public class GetUserAccountsResponse {

    public static class BriefUserAccount {

        private Long accountId;

        private String accountNo;

        private String accountType;

        private String balance;

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
    }

    private Collection<BriefUserAccount> userAccounts;

    public Collection<BriefUserAccount> getUserAccounts() {
        return userAccounts;
    }

    public void setUserAccounts(Collection<BriefUserAccount> userAccounts) {
        this.userAccounts = userAccounts;
    }
}
