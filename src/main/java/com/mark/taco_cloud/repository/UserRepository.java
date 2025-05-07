package com.mark.taco_cloud.repository;

import com.mark.taco_cloud.domain.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

}
