package com.example.onlineide.domain;

import lombok.Data;

import javax.persistence.Embeddable;

@Embeddable
@Data
public class Address {
    private String city;
    private String zipcode;
    private String etc;
}
