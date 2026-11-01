package com.cipherx.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "admins")
public class Admin extends User {

    public Admin() {
        super();
    }

    public Admin(int userId, String username, String email) {
        super(userId, username, email);
    }

    public Admin(int userId, String username, String email, String password) {
        super(userId, username, email, password);
    }

    @Override
    public String getRole() {
        return "ADMIN";
    }
}