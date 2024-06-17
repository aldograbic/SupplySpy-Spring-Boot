package com.project.SupplySpy.repositories.users;

import java.sql.PreparedStatement;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.project.SupplySpy.classes.User;

@Repository
public class JdbcUserRepository implements UserRepository{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public User findByUsername(String username) {
        String sql = "SELECT user_id, username, password, email, role, is_approved, created_at FROM users WHERE username = ?";
        List<User> users = jdbcTemplate.query(sql, new UserRowMapper(), username);
        return users.isEmpty() ? null : users.get(0);
    }

    @Override
    public User findByEmail(String email) {
        String sql = "SELECT user_id, username, password, email, role, is_approved, created_at FROM users WHERE email = ?";
        List<User> users = jdbcTemplate.query(sql, new UserRowMapper(), email);
        return users.isEmpty() ? null : users.get(0);
    }

    @Override
    public User findByUserId(int userId) {
        String sql = "SELECT user_id, username, password, email, role, is_approved, created_at FROM users WHERE user_id = ?";
        List<User> users = jdbcTemplate.query(sql, new UserRowMapper(), userId);
        return users.isEmpty() ? null : users.get(0);
    }

    @Override
    public List<User> findByRole(String role) {
        String sql = "SELECT user_id, username, password, email, role, is_approved, created_at FROM users WHERE role = ?";
        return jdbcTemplate.query(sql, new UserRowMapper(), role);
    }

    @Override
    public void insertUser(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO users (username, email, password) VALUES (?, ?, ?)", new String[]{"user_id"});
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            return ps;
        }, keyHolder);
        user.setUserId(keyHolder.getKey().intValue());
    }

    @Override
    public void approveUserByUserId(int userId) {
        String sql = "UPDATE users SET is_approved = true WHERE user_id = ?";
        jdbcTemplate.update(sql, userId);
    }

    @Override
    public void updateUser(User user) {
        String sql = "UPDATE users SET username = ?, email = ? WHERE user_id = ?";
        jdbcTemplate.update(sql, user.getUsername(), user.getEmail(), user.getUserId());
    }

    @Override
    public void deleteUser(User user) {
        String sql = "DELETE FROM users WHERE user_id = ?";
        jdbcTemplate.update(sql, user.getUserId());
    }
    
    @Override
    public void updatePassword(User user) {
        String sql = "UPDATE users SET password = ? WHERE user_id = ?";
        jdbcTemplate.update(sql, user.getPassword(), user.getUserId());
    }
}