package com.app.ecom.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.app.ecom.enums.USER_STATUS;
import com.app.ecom.model.User;

public interface AdminService {

    public User changeUserStatus(Long id, USER_STATUS status);
    public List<User> getUsersByStatus(USER_STATUS status);
    public Long getAllUsersNumbers();
    public Long getUserCountByMonth(int year, int month);
    public Long getThisMonthUserCount();
    public Page<User> getUsers(int page, int size, String sortBy, String search);

}