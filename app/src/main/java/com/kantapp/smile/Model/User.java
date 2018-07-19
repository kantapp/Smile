package com.kantapp.smile.Model;

/**
 * Created by Kantapp Inc. on 7/19/2018.
 */
public class User {
    public static final String ID="id";
    public static final String EMAIL="email";
    public static final String FULLNAME="fullname";
    public static final String PROFILE="profile";
    public static final String GENDER="gender";
    public static final String TOKEN="token";

    private int id;
    private String email;
    private String fullname;
    private String profile;
    private String gender;
    private String token;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User(int id, String email, String fullname, String profile, String gender, String token) {
        this.id = id;
        this.email = email;
        this.fullname = fullname;
        this.profile = profile;
        this.gender = gender;
        this.token = token;
    }

    public User() {
    }
}
