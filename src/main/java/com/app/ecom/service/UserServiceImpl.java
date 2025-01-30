package com.app.ecom.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.ecom.config.JwtUtil;
import com.app.ecom.enums.USER_ROLE;
import com.app.ecom.exception.AccessDeniedException;
import com.app.ecom.model.Cart;
import com.app.ecom.model.User;
import com.app.ecom.repository.CartRepository;
import com.app.ecom.repository.UserRepository;
import com.app.ecom.request.CreateUserReq;
import com.app.ecom.request.LoginReq;
import com.app.ecom.response.AuthResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    @Override
    public void register(CreateUserReq req) {
        if (userRepository.findByEmail(req.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        User newUser = User.builder()
                .email(req.getEmail())
                .password(passwordEncoder.encode(req.getPassword()))
                .role(USER_ROLE.ROLE_USER)
                .fullName(req.getFullName())
                .mobile(req.getMobile())
                .build();

        userRepository.save(newUser);

        Cart cart = new Cart();
        cart.setUser(newUser);
        cartRepository.save(cart);
    }

    @Override
    public User getUser(String jwt) {

        jwt = jwt.substring(7);
        String email = jwtUtil.extractUsername(jwt);

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public AuthResponse login(LoginReq req) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect email or password", e);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(req.getEmail());


        String role = userDetails.getAuthorities().stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("User has no roles assigned"))
                .getAuthority();


        final String jwt = jwtUtil.generateToken(userDetails);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Successfully logged in");
        authResponse.setRole(role);
        return authResponse;
    }

    @Override
    public AuthResponse adminLogin(LoginReq req) {

        User user = userRepository.findByEmail(req.getEmail()).orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getRole() != USER_ROLE.ROLE_ADMIN){
            throw new AccessDeniedException("You do not have permission to access this resource");
        }

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect email or password", e);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(req.getEmail());


        String role = userDetails.getAuthorities().stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("User has no roles assigned"))
                .getAuthority();

        if(role == "ROLE_USER"){
            throw new AccessDeniedException("You do not have permission to access this resource");
        }
        final String jwt = jwtUtil.generateToken(userDetails);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Successfully logged in");
        authResponse.setRole(role);
        return authResponse;
    }

}