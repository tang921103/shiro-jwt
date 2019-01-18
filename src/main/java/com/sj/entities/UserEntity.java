package com.sj.entities;

import lombok.Data;

import java.io.Serializable;
@Data
public class UserEntity implements Serializable {
    private Integer id;
    private String username;
    private String password;
    private String email;
    private String roles;
    private String permissions;
}
