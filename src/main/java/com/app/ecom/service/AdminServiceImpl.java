package com.app.ecom.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.app.ecom.enums.USER_STATUS;
import com.app.ecom.model.User;
import com.app.ecom.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final UserRepository userRepository;

    @Override
    public User changeUserStatus(Long id, USER_STATUS status) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        USER_STATUS userStatus = user.getStatus();

        if (userStatus != status) {
            user.setStatus(status);
            return userRepository.save(user);   
        }
        return user;
    }

    @Override
    public List<User> getUsersByStatus(USER_STATUS status) {
        List<User> users = userRepository.findByStatus(status);
        return users;
    }

    @Override
    public Long getAllUsersNumbers() {
        Long usersNumbers = userRepository.getTotalUsersCount();
        return usersNumbers;
    }

    @Override
    public Long getUserCountByMonth(int year, int month) {
        return userRepository.countUsersByMonth(year, month);
    }

    @Override
    public Long getThisMonthUserCount() {
        LocalDate today = LocalDate.now();
        
        int monthNumber = today.getMonthValue();
        int year = today.getYear();

        return userRepository.countUsersByMonth(year, monthNumber);
        
    }

    @Override
    public Page<User> getUsers(int page, int size, String sortBy, String search) {

        String sortDirection = "asc"; 
        Sort sort = Sort.by(
            sortDirection.equalsIgnoreCase("desc") ? Sort.Order.desc(sortBy) : Sort.Order.asc(sortBy)
        );
        
        Pageable pageable = PageRequest.of(page, size, sort);
        
        if (search != null && !search.isEmpty()) {
            return userRepository.searchUsers(search, pageable);
        }
        return userRepository.findAll(pageable);
    }

}
