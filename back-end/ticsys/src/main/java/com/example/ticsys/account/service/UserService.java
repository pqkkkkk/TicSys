package com.example.ticsys.account.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ticsys.account.dao.IUserDao;
import com.example.ticsys.account.model.User;
@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final IUserDao userDao;
    @Autowired
    public UserService(IUserDao userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }
    public List<User> GetAllUsers(String role) {
        return userDao.GetAllUsers(role);
    }
    public User GetUserByUsername(String username) {
        return userDao.getUserByUsername(username);
    }
    @Transactional
    public boolean CreateUser(User user) {
        user.setPassWord(passwordEncoder.encode(user.getPassWord()));
        if(userDao.createUser(user)){
            return userDao.addRolesToUser(user.getUserName(), user.getRoles());
        } else {
            return false;
        }
    }
    
}
