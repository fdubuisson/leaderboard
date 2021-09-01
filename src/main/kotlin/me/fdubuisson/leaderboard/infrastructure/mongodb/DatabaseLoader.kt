package me.fdubuisson.leaderboard.infrastructure.mongodb

import com.mongodb.client.MongoDatabase
import com.typesafe.config.Config
import org.litote.kmongo.KMongo

class DatabaseLoader(
    private val config: Config
) {

    fun load(): MongoDatabase {
        val uri = config.getString("mongodb.url")
        val dbName = config.getString("mongodb.db")

        return KMongo.createClient(uri).getDatabase(dbName)
    }
}
