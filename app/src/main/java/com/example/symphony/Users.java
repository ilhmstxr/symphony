package com.example.symphony;

public class Users {
    private String username;
    private String password;
    private String birthday;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Users(String username, String password, String birthday) {
        this.username = username;
        this.password = password;
        this.birthday = birthday;
    }
}
