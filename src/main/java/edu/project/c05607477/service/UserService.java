package edu.project.c05607477.service;

import edu.project.c05607477.exceptions.EmptyAccountException;
import edu.project.c05607477.exceptions.UserNotFoundException;
import edu.project.c05607477.jpa.entity.Account;
import edu.project.c05607477.jpa.entity.AccountType;
import edu.project.c05607477.jpa.entity.User;
import edu.project.c05607477.jpa.repository.AccountRepository;
import edu.project.c05607477.jpa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.UUID;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User getUser(Long userId) {
        if (userId == null || userId <= 0L) {
            throw new UserNotFoundException();
        }

        User user = userRepository.findOne(userId);
        if (user == null) {
            throw new UserNotFoundException();
        }

        return user;
    }

    public Collection<Account> getUserAccounts(Long userId) {
        User user = getUser(userId);

        Collection<Account> accounts = user.getAccounts();
        if (accounts == null || accounts.isEmpty()) {
            throw new EmptyAccountException();
        }

        return accounts;
    }

    public Long createUser(User user) {
        userRepository.saveAndFlush(user);
        return user.getUserId();
    }

}
