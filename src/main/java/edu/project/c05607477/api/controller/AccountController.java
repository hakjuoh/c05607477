package edu.project.c05607477.api.controller;

import edu.project.c05607477.api.model.CreateAccountRequest;
import edu.project.c05607477.api.model.CreateAccountResponse;
import edu.project.c05607477.api.model.GetAccountInformationRequest;
import edu.project.c05607477.api.model.GetAccountInformationResponse;
import edu.project.c05607477.jpa.entity.Account;
import edu.project.c05607477.jpa.entity.AccountType;
import edu.project.c05607477.jpa.entity.User;
import edu.project.c05607477.service.AccountService;
import edu.project.c05607477.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/accounts")
public class AccountController {

    @Autowired
    private UserService userService;

    @Autowired
    private AccountService accountService;

    @RequestMapping(path = "/{accountId}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public GetAccountInformationResponse getAccountInformation(
            GetAccountInformationRequest getAccountInformationRequest) {

        Long accountId = getAccountInformationRequest.getAccountId();
        Account account = accountService.getAccount(accountId);

        GetAccountInformationResponse response = new GetAccountInformationResponse();
        response.setAccountNo(account.getAccountNo());
        response.setAccountType(account.getAccountType());
        response.setBalance(account.getBalance());
        return response;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    @ResponseBody
    public CreateAccountResponse createAccount(
            @RequestBody CreateAccountRequest createAccountRequest) {

        Long userId = createAccountRequest.getUserId();
        User user = userService.getUser(userId);

        AccountType accountType = createAccountRequest.getAccountType();
        Long accountId = accountService.createAccount(user, accountType);

        CreateAccountResponse response = new CreateAccountResponse();
        response.setAccountId(accountId);

        return response;
    }
}
