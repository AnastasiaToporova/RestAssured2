package com.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {
    private int id;
    private String userName;
    private String firstName;
    private String lastname;
    private String email;
    private String phone;
    private String password;
    private int userStatus;

    public UserDto setUserName(String userName) {
        this.userName = userName;
        return  this;
    }

    public UserDto setLastname(String lastname) {
        this.lastname = lastname;
        return this;
    }
    public UserDto setId(int id) {
        this.id = id;
        return this;
    }
    public UserDto setPassword(String password) {
        this.password = password;
        return this;
    }
    public UserDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public UserDto setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public UserDto setUserStatus(int userStatus) {
        this.userStatus = userStatus;
        return this;
    }

    public UserDto setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }
}
