package me.fdubuisson.leaderboard.infrastructure.mongodb

import com.typesafe.config.Config
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

class DatabaseLoader(
    private val config: Config
) {

    fun load(): CoroutineDatabase {
        val uri = config.getString("mongodb.url")
        val dbName = config.getString("mongodb.db")

        return KMongo.createClient(uri).coroutine.getDatabase(dbName)
    }
}
