package org.example.data.repositories;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.example.data.models.User;

import java.util.List;

public class UserMongoDb implements UserRepository {
    private final MongoCollection<Document> collection;

    public UserMongoDb(MongoCollection<Document> collection) {
        this.collection = collection;
        }

    @Override
    public User save(User user) {
        collection.insertOne(user.toDocument());
        ObjectId id = user.toDocument().getObjectId("_id");
        return new User(
                id != null ? id.toString() : null,
                user.getUsername(),
                user.getPassword(),
                user.getProfile()
        );

    }

    @Override
    public User findUserById(int userId) {
        return null;
    }

    @Override
    public Long count() {
        return 0L;
    }

    @Override
    public List<User> findAll() {
        return List.of();
    }

    @Override
    public void deleteById(int userId) {

    }
}