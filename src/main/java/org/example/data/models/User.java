package org.example.data.models;


import org.bson.Document;
import org.bson.types.ObjectId;
import org.example.controllers.HashPassword;

public class User {
    private String id;
    private String username;
    private String password;
    private boolean isLoggedIn = false;
    private Profile profile;

    public User(String id, String username, String password, Profile profile) {
        this.id = id;
        this.username = username;
        this.password = HashPassword.hashPassword(password);
        this.profile = (profile != null) ? profile : new Profile();
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
        if (HashPassword.verifyPassword(password, this.password)) {
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
        if (HashPassword.verifyPassword(password, this.password)) this.password = password;
        throw new IllegalArgumentException("Invalid value password");

    }
    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }


    public Document toDocument() {
        Document document = new Document();
        if(this.id != null && !this.id.isEmpty()) {
            document.put("_id", new ObjectId(this.id));
            }
        document.put("username", username);
        document.put("password", password);
        document.put("isLoggedIn", isLoggedIn);
        if (profile != null) {
            Document profileDocument = new Document();
            if(this.profile.getFirstname() != null){
                profileDocument.put("firstname", profile.getFirstname());
            }
            if(this.profile.getLastname() != null){
                profileDocument.put("lastname", profile.getLastname());
            }
            if(this.profile.getEmail() != null){
                profileDocument.put("email", profile.getEmail());
            }
            if(this.profile.getDateOfBirth() != null){
                profileDocument.put("dateOfBirth", profile.getDateOfBirth());
            }
            if(this.profile.getGender() != null){
                profileDocument.put("gender", profile.getGender());
            }
            if(this.profile.getHeight() != null){
                profileDocument.put("height", profile.getHeight());
            }
            if(this.profile.getWeight() != null){
                profileDocument.put("weight", profile.getWeight());
            }
            if(!profileDocument.isEmpty()){
                document.put("profile", profileDocument);
            }
                 }

        return document;

    }


}
