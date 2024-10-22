package com.julienprr.dreamshops.request;

import lombok.Data;

@Data
public class UserCreateRequest {
    private String firstname;
    private String lastname;
    private String email;
    private String password;

}
