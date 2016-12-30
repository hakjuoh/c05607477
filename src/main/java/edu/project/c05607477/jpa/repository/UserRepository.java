package edu.project.c05607477.jpa.repository;

import edu.project.c05607477.jpa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
