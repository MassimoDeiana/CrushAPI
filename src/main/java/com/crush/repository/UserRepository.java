package com.crush.repository;

import com.crush.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
    User findById(String id);
    User findByPhoneNumber(String phoneNumber);

}
