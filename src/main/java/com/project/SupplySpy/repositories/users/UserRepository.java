package com.project.SupplySpy.repositories.users;

import java.util.List;

import com.project.SupplySpy.classes.User;

public interface UserRepository {
    User findByUsername(String username);
    User findByEmail(String email);
    User findByUserId(int userId);
    List<User> findByRole(String role);
    void insertUser(User user);
    void approveUserByUserId(int userId);
    void updateUser(User user);
    void deleteUser(User user);
    void updatePassword(User user);
}