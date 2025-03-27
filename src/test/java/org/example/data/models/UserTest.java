package org.example.data.models;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserTest {
    User user;
    @BeforeEach
    void setUp() {
        user = new User("us001","username", "password");
    }




}