package edu.project.c05607477.component;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UUIDAccountNumberGenerator implements AccountNumberGenerator {

    @Override
    public String generateAccountNumber() {
        return UUID.randomUUID().toString();
    }
}
