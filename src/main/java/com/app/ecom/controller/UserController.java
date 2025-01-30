package com.app.ecom.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.ecom.model.User;
import com.app.ecom.request.CreateUserReq;
import com.app.ecom.request.LoginReq;
import com.app.ecom.response.ApiResponse;
import com.app.ecom.response.AuthResponse;
import com.app.ecom.service.UserService;

import lombok.RequiredArgsConstructor;



@RestController
@RequestMapping()
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

 
    @PostMapping("/auth/signup")
    public ResponseEntity<ApiResponse> registerUser(@RequestBody CreateUserReq request){
        userService.register(request);

        return  ResponseEntity.ok(new ApiResponse("User registered successfully"));
    }

    @PostMapping("/auth/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginReq request){
        AuthResponse authResponse = userService.login(request);

        return ResponseEntity.ok(authResponse);
    }

    @PreAuthorize("has_role('ROLE_ADMIN')")
    @PostMapping("/auth/admin/login")
    public ResponseEntity<AuthResponse> adminLogin(@RequestBody LoginReq request){
        AuthResponse authResponse = userService.adminLogin(request);

        return ResponseEntity.ok(authResponse);
    }

    @GetMapping("/api/v1/profile")
    public User getUserProfile(@RequestHeader("Authorization") String jwt) {
        System.out.println("User jwt : " + jwt);
        return userService.getUser(jwt);
    }

    @GetMapping("/")
    public String Hello() {
        return "Hello World";
    }
    
}
