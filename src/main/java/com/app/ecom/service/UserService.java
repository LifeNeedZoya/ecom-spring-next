package com.app.ecom.service;

import com.app.ecom.model.User;
import com.app.ecom.request.CreateUserReq;
import com.app.ecom.request.LoginReq;
import com.app.ecom.response.AuthResponse;

public interface UserService {
    public void register(CreateUserReq request);
    public AuthResponse login(LoginReq request);
    public User getUser(String jwt);
    public AuthResponse adminLogin(LoginReq request);
}
