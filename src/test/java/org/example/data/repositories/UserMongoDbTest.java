package org.example.data.repositories;
import org.example.data.models.User;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;
@Testcontainers
public class UserMongoDbTest {

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:6.0");


    @BeforeEach
    void setup() {
        MongoConnection.configure(mongoDBContainer.getReplicaSetUrl());

    }
    @AfterEach
    void tearDownAll() {
        MongoConnection.shutdown();
    }

    @Test
    void testSave() {
        UserMongoDb repository = new UserMongoDb(
                MongoConnection.getClient(),
                "testDB",
                "users"
        );

        User user = new User(null, "testUser", "password", null);
        User savedUser = repository.save(user);
        assertNotNull(savedUser.getId());
    }
    @Test
    public void testFindById() {
        UserMongoDb repository = new UserMongoDb(MongoConnection.getClient(), "testDB", "users");
        User user = new User(null, "testUser", "password", null);
        User savedUser = repository.save(user);
        String userId = savedUser.getId();
        User userOne = repository.findUserById(userId);
        assertNotNull(userOne);
        assertEquals("testUser", userOne.getUsername());

    }
}