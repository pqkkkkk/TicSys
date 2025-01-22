package com.example.ticsys.account.model;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class User {
    private String userName;
    private String passWord;
    private String email;
    private String fullName;
    private String phoneNumber;
    private Date birthday;
    private String gender;
    private String avatarPath;
    private List<String> roles;
}
