package org.example.data.repositories;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;


public class MongoConnection {
    private static volatile MongoClient mongoClient;
    private static String connectionString = "mongodb://localhost:27017";
    private static final Object lock = new Object();

    public static void configure(String connectionString) {
        synchronized(lock) {
            if (mongoClient != null) {
                mongoClient.close();
            }
            MongoConnection.connectionString = connectionString;
            mongoClient = null;
        }
    }

    public static MongoClient getClient() {
        if (mongoClient == null) {
            synchronized(lock) {
                if (mongoClient == null) {
                    mongoClient = MongoClients.create(connectionString);
                }
            }
        }
        return mongoClient;
    }

    public static void shutdown() {
        if (mongoClient != null) {
            mongoClient.close();
            mongoClient = null;
        }
    }
}


