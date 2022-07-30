package com.example.onlineide.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
public class LoginForm {
    @NotEmpty
    private String id;
    private String password;
}
