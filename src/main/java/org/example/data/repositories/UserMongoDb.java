package org.example.data.repositories;

import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.example.data.models.*;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserMongoDb implements UserRepository {
    private final String databaseName;
    private final String collectionName;

    public UserMongoDb(MongoClient mongoClient, String databaseName, String collectionName) {
        this.databaseName = databaseName;
        this.collectionName = collectionName;
    }


    private @NotNull MongoCollection<Document> getUsersCollection() {
        try {
            MongoClient client = MongoConnection.getClient(); // Reuse the connection
            return client.getDatabase(databaseName)
                    .getCollection(collectionName);
        } catch (Exception e) {
            throw new RuntimeException("Failed to access users collection", e);
        }
    }

    @Override
    public User save(User user) {
        try{
            MongoCollection<Document> usersCollection = getUsersCollection();
            Document userDocument = user.toDocument();
            usersCollection.insertOne(userDocument);
            user.setId(userDocument.getObjectId("_id").toString());
            System.out.println(user.getUsername() + " saved successfully with id :" + user.getId());
            return user;
        } catch (MongoWriteException e) {
            if (e.getError().getCode() == 11000) {
                String errorMessage = e.getMessage();
                if (errorMessage.contains("username")) {
                    throw new RuntimeException("Username already exists", e);
                } else if (errorMessage.contains("profile.email")) {
                    throw new RuntimeException("Email already exists", e);
                }
            }
            throw new RuntimeException("Failed to save user", e);
        } catch (Exception e) {
            throw new RuntimeException("Failed to save user", e);
        }
    }


    @Override
    public User findUserById(String userId) {
        try {
            MongoCollection<Document> usersCollection = getUsersCollection();
            Document query = new Document("_id", new ObjectId(userId));
            Document userDocument = usersCollection.find(query).first();
            if (userDocument == null) {
                return null;
            }
            return this.documentToUser(userDocument);

        } catch (IllegalArgumentException e) {
        throw new IllegalArgumentException("Invalid user ID format", e);
    } catch (Exception e) {
        throw new RuntimeException("Failed to find user by ID", e);
        }
    }

    @Override
    public User updateUserProfile(String userId, String firstname, String lastname, String email, LocalDate dateOfBirth, Gender gender, Weight weight, Height height) {
        User user = this.findUserById(userId);
        if (user != null) {
            if (firstname != null){
                user.getProfile().setFirstname(firstname);
            }
            if (lastname != null){
                user.getProfile().setLastname(lastname);
            }
            if (email != null){
                user.getProfile().setEmail(email);
            }
            if (dateOfBirth != null){
                user.getProfile().setDateOfBirth(dateOfBirth);
            }
            if (gender != null){
                user.getProfile().setGender(gender);
            }
            if (weight != null){
                user.getProfile().setWeight(weight);
            }
            if (height != null){
                user.getProfile().setHeight(height);
            }
        }
        else return null;

        return save(user);
    }

    @Override
    public User findUserByEmail(String email) {
        try {
            MongoCollection<Document> usersCollection = getUsersCollection();
            Document query = new Document("profile.email", email);
            Document userDocument = usersCollection.find(query).first();
            if (userDocument == null) {
                return null;
            }
            return this.documentToUser(userDocument);
        } catch (Exception e) {
            throw new RuntimeException("Failed to find user by this email: " + email + "try again", e);
        }
    }

    @Override
    public Long count() {
        try {
            MongoCollection<Document> usersCollection = getUsersCollection();
            return usersCollection.countDocuments();
        } catch (Exception e) {
            throw new RuntimeException("Failed to count users", e);
        }
    }

    @Override
    public List<User> findAll() {
        final List<User> users = new ArrayList<>();
        MongoCollection<Document> usersCollection = getUsersCollection();
        try (MongoCursor<Document> cursor = usersCollection.find().iterator()) {
            while (cursor.hasNext()) {
                Document userDocument = cursor.next();
                users.add(documentToUser(userDocument)); // Convert Document → User
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch all users", e);
        }

        return users;
    }

    @Override
    public boolean deleteById(String userId) {
        try {
            MongoCollection<Document> usersCollection = getUsersCollection();
            Document query = new Document("_id", new ObjectId(userId));
            DeleteResult result = usersCollection.deleteOne(query);
            return result.getDeletedCount() == 1;
        }catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid user ID format: " + userId, e);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete user with ID: " + userId, e);
        }
    }

    @Override
    public List<User> finderUserByFilter(Gender gender, Weight weight, Height height) {
        try {
            MongoCollection<Document> usersCollection = getUsersCollection();
            Document query = new Document();
            Profile profile = new Profile()
                    .setHeight(height)
                    .setWeight(weight)
                    .setGender(gender);
            if (profile.getHeight() != null){
                query.put("height", profile.getHeight());
            }
            if (profile.getWeight() != null){
                query.put("weight", profile.getWeight());
            }
            if (profile.getGender() != null){
                query.put("gender", profile.getGender());
            }

            return usersCollection.find(query)
                .map(this::documentToUser)
                .into(new ArrayList<>());
    }catch (Exception e) {
            throw new RuntimeException("Failed to find users by attributes", e);
        }
    }

    private User documentToUser(Document userDocument) {
        Profile profile = null;
        if (userDocument.containsKey("profile")) {
            Document profileDocument = userDocument.get("profile", Document.class);
            profile = new Profile()
                    .setFirstname(profileDocument.getString("firstname"))
                    .setLastname(profileDocument.getString("lastname"))
                    .setEmail(profileDocument.getString("email"))
                    .setDateOfBirth(LocalDate.parse(profileDocument.getString("height")))
                    .setGender(Gender.valueOf(profileDocument.getString("gender")))
                    .setWeight(Weight.valueOf(profileDocument.getString("weight")))
                    .setHeight(Height.valueOf(profileDocument.getString("height")));

        }

        return new User(
                userDocument.getObjectId("_id").toString(),
                userDocument.getString("username"),
                userDocument.getString("password"),
                profile
        );


    }


}