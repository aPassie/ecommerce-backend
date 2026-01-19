package com.shanks.E_Commerce.Backend.repository;

import com.shanks.E_Commerce.Backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
