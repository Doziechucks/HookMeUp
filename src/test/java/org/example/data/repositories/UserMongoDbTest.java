package org.example.data.repositories;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserMongoDbTest {
    UserMongoDb user;

    @BeforeEach
    public void setUp(){
        user = new UserMongoDb();
    }


}