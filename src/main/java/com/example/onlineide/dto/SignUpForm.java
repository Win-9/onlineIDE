package com.example.onlineide.dto;

import lombok.Data;

@Data
public class SignUpForm {
    private String id;
    private String password;
    private String name;
    private String email;
    private String city;
    private String zipcode;
    private String etc;
}
