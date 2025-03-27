package org.example.data.models;


import org.bson.Document;
import org.bson.types.ObjectId;
import org.example.controllers.Passwording;

import java.util.Optional;

public class User {
    private String id;
    private String username;
    private String password;
    private boolean isLoggedIn = false;
    private Profile profile;

    public User(String id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = Passwording.hashPassword(password);
        this.profile = new Profile();
    }
    public User(String id, String username, String password, Profile profile) {
        this.id = id;
        this.username = username;
        this.password = Passwording.hashPassword(password);
        this.profile = profile;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void login(String password) {
        if (Passwording.verifyPassword(password, this.password)) {
            this.isLoggedIn = true;
        }
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }

    public String getPassword() {
        return password;
    }

    public void changePassword(String password) {
        if (Passwording.verifyPassword(password, this.password)) this.password = password;
        throw new IllegalArgumentException("Invalid value password");


    }
    public Document toDocument() {
        return new Document("_id", new ObjectId(id))
                .append("username", username)
                .append("password", password);
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}
