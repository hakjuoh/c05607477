package edu.project.c05607477.api;

import edu.project.c05607477.Application;
import edu.project.c05607477.api.model.CreateAccountRequest;
import edu.project.c05607477.api.model.CreateAccountResponse;
import edu.project.c05607477.api.model.GetUserAccountsResponse;
import edu.project.c05607477.jpa.entity.AccountType;
import edu.project.c05607477.jpa.entity.User;
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
public class AccountControllerTests {

    @Autowired
    private UserService userService;

    @Autowired
    private TestRestTemplate restTemplate;

    private Random random = new Random();

    @Test
    public void createUserAccountTest() {
        User testUser = createTestUser();
        Long userId = testUser.getUserId();
        CreateAccountRequest request = new CreateAccountRequest();
        request.setUserId(userId);
        request.setAccountType(AccountType.Checking);

        ResponseEntity<CreateAccountResponse> createAccountResponseEntity =
                this.restTemplate.postForEntity("/api/v1/accounts", request, CreateAccountResponse.class);

        assertThat(createAccountResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        CreateAccountResponse createAccountResponse = createAccountResponseEntity.getBody();
        assertThat(createAccountResponse).isNotNull();

        Long accountId = createAccountResponse.getAccountId();
        assertThat(accountId).isGreaterThan(0L);


        ResponseEntity<GetUserAccountsResponse> getUserAccountsResponseEntity =
                this.restTemplate.getForEntity("/api/v1/users/{userId}/accounts", GetUserAccountsResponse.class, userId);

        assertThat(getUserAccountsResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        GetUserAccountsResponse getUserAccountsResponse = getUserAccountsResponseEntity.getBody();
        Collection<GetUserAccountsResponse.BriefUserAccount> userAccounts = getUserAccountsResponse.getUserAccounts();
        assertThat(userAccounts.size()).isEqualTo(1);

        GetUserAccountsResponse.BriefUserAccount userAccount = userAccounts.iterator().next();
        assertThat(userAccount.getAccountId()).isEqualTo(accountId);
        assertThat(userAccount.getAccountType()).isEqualTo(request.getAccountType());
        assertThat(userAccount.getBalance()).isEqualTo(BigInteger.ZERO);
    }

    private User createTestUser() {
        User testUser = new User();
        testUser.setAddress("test address");
        testUser.setEmail("test email");
        testUser.setName("test name");
        testUser.setPinCode(Math.abs(random.nextInt(10000)));

        userService.createUser(testUser);
        return testUser;
    }
}
