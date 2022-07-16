package com.example.onlineide.domain;


import javax.persistence.Embeddable;

@Embeddable
public class Code {
    private String language;
    private String code;
}
