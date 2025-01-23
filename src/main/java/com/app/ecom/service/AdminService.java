package com.app.ecom.service;

import com.app.ecom.model.Admin;
import com.app.ecom.request.LoginReq;
import com.app.ecom.response.AuthResponse;

public interface AdminService {
    Admin addAdmin(Admin admin);
    Admin updateAdmin(Admin admin, Long id);
    AuthResponse loginAdmin(LoginReq request);
    
} 