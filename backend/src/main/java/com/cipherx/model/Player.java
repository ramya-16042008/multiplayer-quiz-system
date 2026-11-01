package com.cipherx.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "players")
public class Player extends User {

    public Player() {
        super();
    }

    public Player(int userId, String username, String email) {
        super(userId, username, email);
    }

    public Player(int userId, String username, String email, String password) {
        super(userId, username, email, password);
    }

    @Override
    public String getRole() {
        return "PLAYER";
    }
}