package me.fdubuisson.leaderboard

import com.mongodb.MongoException
import com.mongodb.client.MongoClients
import com.typesafe.config.ConfigFactory
import org.bson.BsonDocument
import org.bson.BsonInt64

fun main() {
    val config = ConfigFactory.load()
    val uri = config.getString("mongodb.url")

    println(uri)

    MongoClients.create(uri).use { mongoClient ->
        val database = mongoClient.getDatabase("admin")
        try {
            val command = BsonDocument("ping", BsonInt64(1))
            val commandResult = database.runCommand(command)
            println("Connected successfully to server.")
        } catch (me: MongoException) {
            System.err.println("An error occurred while attempting to run a command: $me")
        }
    }
}