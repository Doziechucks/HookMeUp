package org.example.services;

import org.example.data.models.Gender;
import org.example.data.models.Height;
import org.example.data.models.User;
import org.example.data.models.Weight;

import java.util.List;

public interface UserServices {
    void saveUser(User user);
    void updateUser(User user);
    void deleteUser(User user);
    User getUserByEmail(String email);
    List<User> getUsers();
    long getUsersCount();
    List<User> findUserByFilter(Gender gender, Weight weight, Height height);
}
