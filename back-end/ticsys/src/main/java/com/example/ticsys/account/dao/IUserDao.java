package com.example.ticsys.account.dao;

import java.util.List;

import com.example.ticsys.account.model.User;

public interface IUserDao {
    public List<User> GetAllUsers(String role);
    public User getUserByUsername(String username);
    public boolean createUser(User user);
    public boolean addRolesToUser(String username, List<String> roles);
}
