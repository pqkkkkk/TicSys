package com.example.ticsys.account.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.ticsys.account.model.OrganizerInfo;
import com.example.ticsys.account.model.User;
import com.example.ticsys.account.service.UserService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/account/user")
@Slf4j
public class UserController {
    private final UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/{username}")
    @PreAuthorize("@accountSecurityServiceImpl.IsAccountOwner(#username)")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        log.info("getUserByUsername of UserController, username: ", username);
        User user = userService.GetUserByUsername(username);
        if(user == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(user);
        }
    }
    @GetMapping
    public ResponseEntity<List<User>> GetAllUsers(@RequestParam(required = false) String role)
    {
        List<User> users = userService.GetAllUsers(role);
        return ResponseEntity.ok(users);
    }
    @PostMapping
    public ResponseEntity<String> signup(@RequestBody User user) {
        if(userService.CreateUser(user)){
            return ResponseEntity.ok("successfully");
        } else {
            return ResponseEntity.badRequest().body("failed");
        }
    }
    @PostMapping("/organizer")
    @PreAuthorize("hasAnyRole('ADMIN', 'ORGANIZER', 'USER')")
    public ResponseEntity<String> registerForOrganizer(@ModelAttribute OrganizerInfo organizerInfo, @RequestParam MultipartFile organizerAvt) {
        if(userService.RegisterforOrganizer(organizerInfo, organizerAvt)){
            return ResponseEntity.ok("successfully");
        } else {
            return ResponseEntity.badRequest().body("failed");
        }
    }
}
