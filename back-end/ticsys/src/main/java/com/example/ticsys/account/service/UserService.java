package com.example.ticsys.account.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import com.example.ticsys.account.dao.IUserDao;
import com.example.ticsys.account.model.OrganizerInfo;
import com.example.ticsys.account.model.User;
import com.example.ticsys.media.CloudinaryService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final IUserDao userDao;
    private final CloudinaryService cloudinaryService;
    
    @Autowired
    public UserService(IUserDao userDao, PasswordEncoder passwordEncoder, CloudinaryService cloudinaryService) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.cloudinaryService = cloudinaryService;
    }
    public List<User> GetAllUsers(String role) {
        return userDao.GetAllUsers(role);
    }
    public User GetUserByUsername(String username) {
        log.info("GetUserByUsername of UserService");
        try{
            return userDao.GetUserByUsername(username);
        }
        catch(Exception e){
            log.error("Error in GetUserByUsername of UserService: " + e.getMessage());
            return null;
        }
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
    @Transactional
    public boolean RegisterforOrganizer(OrganizerInfo organizerInfo, MultipartFile organizerAvt) {
        String avatarPath = "";
        try{
            avatarPath = cloudinaryService.uploadFile(organizerAvt);

            if(!avatarPath.equals("")) {
                throw new Exception("Failed to upload avatar");
            }
            if(!userDao.AddOrganizerInfo(organizerInfo)) {
                throw new Exception("Failed to add organizer info");
            }
            if(!userDao.UpdateAvatarOfUser(organizerInfo.getUserId(), avatarPath)) {
                throw new Exception("Failed to update avatar");
            }
            if(!userDao.addRolesToUser(organizerInfo.getUserId(), List.of("ORGANIZER"))) {
                throw new Exception("Failed to add role");
            }
            return true;
        } 
        catch(Exception e) {
            if(avatarPath != "") {
                String deleteResult = cloudinaryService.deleteFile(avatarPath);
                log.info("RegisterForOrganizer of UserSerivce, delete avatar result: " + deleteResult);
            }
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
    }
}
