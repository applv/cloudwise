package com.applv.cloudwise.repository;

import com.applv.cloudwise.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Integer> {

  Optional<User> findUserByName(String name);

  Optional<User> findUserById(Integer id);
}
