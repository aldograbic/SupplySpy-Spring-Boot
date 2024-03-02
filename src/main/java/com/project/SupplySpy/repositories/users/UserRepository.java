package com.project.SupplySpy.repositories.users;

import java.util.List;

import com.project.SupplySpy.classes.User;

public interface UserRepository {
    User findByUsername(String username);
    User findByEmail(String email);
    List<User> findByRole(String role);
    void insertUser(User user);
    void approveUserByUsername(String username);
}