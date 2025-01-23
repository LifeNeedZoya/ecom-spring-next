package com.app.ecom.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.ecom.model.Admin;
import com.app.ecom.request.LoginReq;
import com.app.ecom.response.AuthResponse;
import com.app.ecom.service.AdminService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/auth")
public class AdminController {
    private final AdminService adminService;

    @PostMapping("/add")
    public ResponseEntity<String> addAdmin(@RequestBody Admin admin) {
        adminService.addAdmin(admin);

        return ResponseEntity.ok("Admin added successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> adminLogin(@RequestBody LoginReq req) {
        AuthResponse response = adminService.loginAdmin(req);
        System.out.println("RESPONSE FROM LOGIN:" + response);
        return ResponseEntity.ok(response);
    }
}
