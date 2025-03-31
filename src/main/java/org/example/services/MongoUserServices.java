package org.example.services;

import com.mongodb.DuplicateKeyException;
import com.mongodb.MongoException;
import org.example.data.models.Gender;
import org.example.data.models.Height;
import org.example.data.models.User;
import org.example.data.models.Weight;
import org.example.data.repositories.UserMongoDb;

import java.util.List;

public class MongoUserServices implements UserServices {
    private final UserMongoDb repository;

    public MongoUserServices(UserMongoDb repository) {
        this.repository = repository;
    }

    @Override
    public User saveUser(User user) {
        try {
            User savedUser = repository.save(user);
            if (savedUser == null) {
                throw new UserNotSavedException("Invalid user details");
            }
            return savedUser;
        } catch (DuplicateKeyException e) {
            throw new UserNotSavedException("Email already exists", e);
        } catch (MongoException e) {
            throw new UserNotSavedException("Database error while saving user", e);
        }
    }

    @Override
    public void updateUser(User user) {

    }

    @Override
    public void deleteUser(User user) {

    }

    @Override
    public User getUserByEmail(String email) {
        return null;
    }

    @Override
    public List<User> getUsers() {
        return List.of();
    }

    @Override
    public long getUsersCount() {
        return 0;
    }

    @Override
    public List<User> findUserByFilter(Gender gender, Weight weight, Height height) {
        return List.of();
    }
}
