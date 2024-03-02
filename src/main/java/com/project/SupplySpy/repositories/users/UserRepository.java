package com.project.SupplySpy.repositories.users;

import com.project.SupplySpy.classes.User;

public interface UserRepository {
    User findByUsername(String username);
    User findByEmail(String email);
    void insertUser(User user);
}