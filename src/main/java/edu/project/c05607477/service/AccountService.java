package edu.project.c05607477.service;

import edu.project.c05607477.component.AccountNumberGenerator;
import edu.project.c05607477.exceptions.AccountNotFoundException;
import edu.project.c05607477.jpa.entity.Account;
import edu.project.c05607477.jpa.entity.AccountType;
import edu.project.c05607477.jpa.entity.User;
import edu.project.c05607477.jpa.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigInteger;

@Service
@Transactional
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountNumberGenerator accountNumberGenerator;

    public Long createAccount(User user, AccountType accountType) {

        Account account = new Account();
        account.setUser(user);
        account.setAccountNo(accountNumberGenerator.generateAccountNumber());
        account.setAccountType(accountType);
        account.setBalance(BigInteger.ZERO);

        accountRepository.saveAndFlush(account);

        return account.getAccountId();
    }

    public Account getAccount(Long accountId) {
        if (accountId == null || accountId <= 0L) {
            throw new AccountNotFoundException();
        }

        Account account = accountRepository.findOne(accountId);
        if (account == null) {
            throw new AccountNotFoundException();
        }

        return account;
    }
}
