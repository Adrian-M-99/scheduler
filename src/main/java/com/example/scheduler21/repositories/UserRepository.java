package com.example.scheduler21.repositories;

import com.example.scheduler21.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface UserRepository extends JpaRepository<User, Integer> {
}
