package org.tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tracker.model.User;

public interface UserRepository extends JpaRepository<User, Long> {}
