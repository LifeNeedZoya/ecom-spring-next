package com.app.ecom.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.ecom.config.JwtUtil;
import com.app.ecom.model.Admin;
import com.app.ecom.repository.AdminRepository;
import com.app.ecom.request.LoginReq;
import com.app.ecom.response.AuthResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final CustomAdminDetailsService customAdminDetailsService;
    private final JwtUtil jwtUtil;

    @Override
    public Admin addAdmin(Admin admin) {

        Admin newAdmin = Admin.builder()
                .email(admin.getEmail())
                .password(passwordEncoder.encode(admin.getPassword()))
                .role("ROLE_ADMIN")
                .build();

        return adminRepository.save(newAdmin);
    }

    @Override
    public Admin updateAdmin(Admin admin, Long id) {
        Admin existingAdmin = adminRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException("No admin found with id: " + id));

        if (admin.getEmail()!=null) {
            existingAdmin.setEmail(admin.getEmail());
        }

        if (admin.getPassword()!=null) {
            existingAdmin.setPassword(passwordEncoder.encode(admin.getPassword()));
        }

        return  adminRepository.save(existingAdmin);
    }

    @Override
    public AuthResponse loginAdmin(LoginReq req) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect email or password", e);
        }

        UserDetails adminDetails =  customAdminDetailsService.loadUserByUsername(req.getEmail());
        String jwt = jwtUtil.generateToken(adminDetails);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Admin successfully logged in");

        return authResponse;
    }
}
