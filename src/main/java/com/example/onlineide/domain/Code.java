package com.example.onlineide.domain;


import lombok.Data;

import javax.persistence.Embeddable;

@Embeddable
@Data
public class Code {
    private String language;
    private String code;

    public Code(String language, String code) {
        this.language = language;
        this.code = code;
    }

    public Code() {
    }
}
