package com.hiremepls.turodemoproject.repository;

import com.hiremepls.turodemoproject.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<Users, Integer> {
    List<Users> findUserById(Integer id);
}
