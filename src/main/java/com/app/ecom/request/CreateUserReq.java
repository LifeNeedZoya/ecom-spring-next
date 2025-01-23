package com.app.ecom.request;

import lombok.Data;
import lombok.NonNull;

@Data
public class CreateUserReq {
    @NonNull
    private String fullName;

    @NonNull
    private String password;

    @NonNull
    private String email;

    @NonNull
    private String mobile;
}
