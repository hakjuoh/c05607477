package edu.project.c05607477.api;

import edu.project.c05607477.Application;
import edu.project.c05607477.api.model.*;
import edu.project.c05607477.jpa.entity.Account;
import edu.project.c05607477.jpa.entity.AccountType;
import edu.project.c05607477.jpa.entity.TxStatus;
import edu.project.c05607477.jpa.entity.User;
import edu.project.c05607477.service.AccountService;
import edu.project.c05607477.service.TransactionService;
import edu.project.c05607477.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TransactionControllerTests {

    @Autowired
    private UserService userService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private TestRestTemplate restTemplate;

    private Random random = new Random();

    @Test
    public void transferToTest() {
        User testUser1 = createTestUser();
        User testUser2 = createTestUser();

        Account testAccount1 = createTestAccount(testUser1);
        Account testAccount2 = createTestAccount(testUser2);

        Long testSenderAccountId = testAccount1.getAccountId();
        Long testRecipientAccountId = testAccount2.getAccountId();

        BigInteger amount = BigInteger.valueOf(100L);
        transactionService.deposit(testAccount1, amount);
        testAccount1 = accountService.getAccount(testSenderAccountId);

        assertThat(testAccount1.getBalance()).isEqualTo(amount);

        TransferToRequest request = new TransferToRequest();
        request.setSenderAccountId(testSenderAccountId);
        request.setRecipientAccountId(testRecipientAccountId);
        request.setAmount(amount);

        ResponseEntity<TransferToResponse> transferToResponseResponseEntity =
                this.restTemplate.postForEntity("/api/v1/transactions/transferTo", request, TransferToResponse.class);

        assertThat(transferToResponseResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        TransferToResponse transferToResponse = transferToResponseResponseEntity.getBody();
        assertThat(transferToResponse).isNotNull();
        assertThat(transferToResponse.getTxStatus()).isEqualTo(TxStatus.Success);

        testAccount1 = accountService.getAccount(testSenderAccountId);
        testAccount2 = accountService.getAccount(testRecipientAccountId);

        assertThat(testAccount1.getBalance()).isEqualTo(BigInteger.ZERO);
        assertThat(testAccount2.getBalance()).isEqualTo(amount);
    }

    private User createTestUser() {
        User testUser = new User();

        int pinCode = Math.abs(random.nextInt(10000));
        testUser.setAddress("test address " + pinCode);
        testUser.setEmail("test email " + pinCode);
        testUser.setName("test name " + pinCode);
        testUser.setPinCode(pinCode);

        userService.createUser(testUser);
        return testUser;
    }

    private Account createTestAccount(User user) {
        Long accountId = accountService.createAccount(user, AccountType.Checking);
        return accountService.getAccount(accountId);
    }
}
