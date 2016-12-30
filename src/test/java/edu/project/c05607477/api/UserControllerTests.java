package edu.project.c05607477.api;

import edu.project.c05607477.Application;
import edu.project.c05607477.api.model.CreateUserRequest;
import edu.project.c05607477.api.model.CreateUserResponse;
import edu.project.c05607477.api.model.ErrorResponse;
import edu.project.c05607477.api.model.GetUserPreferencesResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTests extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private TestRestTemplate restTemplate;

    private Random random = new Random();

    @Test
    public void getUserPreferencesWithInvalidUserIdTest() {
        Long userId = 0L;
        ResponseEntity<ErrorResponse> responseEntity = getUserPreferences(userId, ErrorResponse.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

        ErrorResponse errorResponse = responseEntity.getBody();
        assertThat(errorResponse).isNotNull();

        assertThat(errorResponse.getErrorCode()).isEqualTo("404");
        assertThat(errorResponse.getErrorMessage()).isEqualTo("Requested user doesn't exist");
    }

    private <T> ResponseEntity<T> getUserPreferences(Long userId, Class<T> responseType) {
        return this.restTemplate.getForEntity("/api/v1/users/{userId}", responseType, userId);
    }

    @Test
    public void createUserTest() {
        CreateUserRequest request = new CreateUserRequest();
        request.setAddress("test address");
        request.setEmail("test email");
        request.setName("test name");
        request.setPinCode(Math.abs(random.nextInt(10000)));

        ResponseEntity<CreateUserResponse> createUserResponseEntity =
                this.restTemplate.postForEntity("/api/v1/users", request, CreateUserResponse.class);

        CreateUserResponse createUserResponse = createUserResponseEntity.getBody();
        assertThat(createUserResponse).isNotNull();

        Long userId = createUserResponse.getUserId();
        assertThat(userId).isGreaterThan(0L);

        ResponseEntity<GetUserPreferencesResponse> getUserPreferencesResponseEntity =
                getUserPreferences(userId, GetUserPreferencesResponse.class);

        assertThat(getUserPreferencesResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        GetUserPreferencesResponse getUserPreferencesResponse = getUserPreferencesResponseEntity.getBody();

        assertThat(getUserPreferencesResponse).isNotNull();
        assertThat(getUserPreferencesResponse.getAddress()).isEqualTo(request.getAddress());
        assertThat(getUserPreferencesResponse.getEmail()).isEqualTo(request.getEmail());
        assertThat(getUserPreferencesResponse.getName()).isEqualTo(request.getName());
        assertThat(getUserPreferencesResponse.getPinCode()).isEqualTo(request.getPinCode());
    }

}
