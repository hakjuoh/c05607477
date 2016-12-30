package edu.project.c05607477.api.controller;

import edu.project.c05607477.api.model.*;
import edu.project.c05607477.jpa.entity.Account;
import edu.project.c05607477.jpa.entity.AccountType;
import edu.project.c05607477.jpa.entity.User;
import edu.project.c05607477.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(path = "/{userId}", method = RequestMethod.GET)
    public ResponseEntity<GetUserPreferencesResponse> getUserPreferences(
            GetUserPreferencesRequest request) {

        Long userId = request.getUserId();
        User user = userService.getUser(userId);

        GetUserPreferencesResponse response = new GetUserPreferencesResponse();
        response.setAddress(user.getAddress());
        response.setEmail(user.getEmail());
        response.setName(user.getName());
        response.setPinCode(user.getPinCode());
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @RequestMapping(path = "/{userId}/accounts", method = RequestMethod.GET)
    public ResponseEntity<GetUserAccountsResponse> getUserAccounts(
            GetUserAccountsRequest request) {

        Long userId = request.getUserId();
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

        return new ResponseEntity(response, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    @ResponseBody
    public CreateUserResponse createUser(
            @RequestBody CreateUserRequest createUserRequest) {

        User user = new User();
        user.setAddress(createUserRequest.getAddress());
        user.setEmail(createUserRequest.getEmail());
        user.setName(createUserRequest.getName());
        user.setPinCode(createUserRequest.getPinCode());

        Long userId = userService.createUser(user);

        CreateUserResponse response = new CreateUserResponse();
        response.setUserId(userId);

        return response;
    }
}
