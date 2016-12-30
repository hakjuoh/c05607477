package edu.project.c05607477.jpa.repository;

import edu.project.c05607477.jpa.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
