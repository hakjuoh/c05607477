package edu.project.c05607477.component;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UUIDTransactionNumberGenerator implements TransactionNumberGenerator {

    @Override
    public String generateTransactionNumber() {
        return UUID.randomUUID().toString();
    }
}
