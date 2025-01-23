package com.app.ecom.response;

import lombok.Data;

@Data
public class AuthResponse {
    private String jwt;
    private String message;
}
