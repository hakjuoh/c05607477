package edu.project.c05607477.jpa.repository;

import edu.project.c05607477.jpa.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
