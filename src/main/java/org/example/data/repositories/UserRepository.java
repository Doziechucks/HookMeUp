package org.example.data.repositories;

import org.example.data.models.*;

import java.time.LocalDate;
import java.util.List;

public interface UserRepository {
    User save(User user);
    User findUserById(String userId);
    User updateUserProfile(String userId, String firstname, String lastname, String email, LocalDate dateOfBirth, Gender gender, Weight weight, Height height);
    User findUserByEmail(String email);
    Long count();
    List<User> findAll();
    boolean deleteById(String userId);
    List<User> finderUserByFilter(Gender gender, Weight weight, Height height);

}
