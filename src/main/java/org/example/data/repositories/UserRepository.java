package org.example.data.repositories;

import org.example.data.models.User;

import java.util.List;

public interface UserRepository {
    User save(User user);
    User findUserById(int userId);
    Long count();
    List<User> findAll();
    void deleteById(int userId);

}
