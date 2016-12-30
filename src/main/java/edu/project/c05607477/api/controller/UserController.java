package edu.project.c05607477.api.controller;

import edu.project.c05607477.api.model.GetUserAccountsRequest;
import edu.project.c05607477.api.model.GetUserAccountsResponse;
import edu.project.c05607477.api.model.GetUserPreferencesRequest;
import edu.project.c05607477.api.model.GetUserPreferencesResponse;
import edu.project.c05607477.jpa.entity.Account;
import edu.project.c05607477.jpa.entity.User;
import edu.project.c05607477.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET, value = "/{userId}")
    public GetUserPreferencesResponse getUserPreferences(
            GetUserPreferencesRequest getUserPreferencesRequest) {

        Long userId = getUserPreferencesRequest.getUserId();
        User user = userService.getUser(userId);

        GetUserPreferencesResponse response = new GetUserPreferencesResponse();
        response.setAddress(user.getAddress());
        response.setEmail(user.getEmail());
        response.setName(user.getName());
        response.setPinCode(user.getPinCode());
        return response;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{userId}/accounts")
    public GetUserAccountsResponse getUserAccounts(
            GetUserAccountsRequest getUserAccountsRequest) {

        Long userId = getUserAccountsRequest.getUserId();
        Collection<Account> accounts = userService.getUserAccounts(userId);

        GetUserAccountsResponse response = new GetUserAccountsResponse();
        response.setUserAccounts(accounts.stream()
                .map(x -> {
                    GetUserAccountsResponse.BriefUserAccount userAccount =
                            new GetUserAccountsResponse.BriefUserAccount();
                    userAccount.setAccountId(x.getAccountId());
                    userAccount.setAccountNo(x.getAccountNo());
                    userAccount.setAccountType(x.getAccountType());
                    userAccount.setBalance(x.getBalance());
                    return userAccount;
                })
                .collect(Collectors.toList()));
        return response;
    }
}
