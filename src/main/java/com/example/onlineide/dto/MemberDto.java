package com.example.onlineide.dto;

import com.example.onlineide.domain.Address;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberDto {
    private String id;
    private String password;
    private String name;

    private Address address;
    private String email;
}
