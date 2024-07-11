package com.banking.springbootbanking.model.api.model;

import lombok.Setter;

@Setter
public class LoginBody {

    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}
