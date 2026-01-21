package com.university.portailstages.dto;

import com.university.portailstages.entity.Role;
import lombok.Data;

@Data
public class RegisterRequest {
    private String nom;
    private String email;
    private String password;
    private Role role;


}
