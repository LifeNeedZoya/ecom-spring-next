package com.app.ecom.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.ecom.enums.USER_STATUS;
import com.app.ecom.model.User;
import com.app.ecom.service.AdminService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @GetMapping
    public ResponseEntity<Page<User>> getUsers(
            @RequestParam(defaultValue = "0") int page,      
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,  
            @RequestParam(required = false) String search) { 
        Page<User> users = adminService.getUsers(page, size, sortBy, search);

        return ResponseEntity.ok(users);
    }

    @PreAuthorize("has_role('ROLE_ADMIN')")
    public ResponseEntity<String> changeUserStatus(Long id, USER_STATUS status) {
        adminService.changeUserStatus(id, status);
        return ResponseEntity.ok("User status changed successfully");
    }

    @PreAuthorize("has_role('ROLE_ADMIN')")
    public ResponseEntity<List<User>> getUsersByStatus(USER_STATUS status){
        return ResponseEntity.ok(adminService.getUsersByStatus(status));
    }

    @PreAuthorize("has_role('ROLE_ADMIN')")
    public ResponseEntity<Long> getAllUsersNumbers(){
        return ResponseEntity.ok(adminService.getAllUsersNumbers());
    }

    @PreAuthorize("has_role('ROLE_ADMIN')")
    public ResponseEntity<Long> getUserCountByMonth(int year, int month){
        return ResponseEntity.ok(adminService.getUserCountByMonth(year, month));
    }

    @PreAuthorize("has_role('ROLE_ADMIN')")
    public ResponseEntity<Long> getThisMonthUserCount(){
        return ResponseEntity.ok(adminService.getThisMonthUserCount());
    }

}
