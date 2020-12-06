package com.melissabakker.newaccount;

public class User {
    private String email;
    private String role;
    private String username;

    public User(String email, String role, String username) {
        this.email = email;
        this.role = role;
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public String getUsername() {return username; }
}
