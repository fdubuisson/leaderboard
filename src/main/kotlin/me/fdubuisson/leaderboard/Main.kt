package me.fdubuisson.leaderboard

import com.mongodb.MongoException
import com.typesafe.config.ConfigFactory
import me.fdubuisson.leaderboard.domain.Player
import me.fdubuisson.leaderboard.domain.PlayerRepository
import me.fdubuisson.leaderboard.infrastructure.mongodb.DatabaseLoader
import me.fdubuisson.leaderboard.infrastructure.mongodb.MongoPlayerRepository
import org.bson.BsonDocument
import org.bson.BsonInt64
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent.inject

fun main() {
    @Suppress("USELESS_CAST")
    val module = module {
        single { ConfigFactory.load() }
        single { DatabaseLoader(get()).load() }
        single { MongoPlayerRepository(get()) as PlayerRepository }
    }

    startKoin {
        modules(module)
    }

    val database = DatabaseLoader(ConfigFactory.load()).load()
    try {
        val command = BsonDocument("ping", BsonInt64(1))
        database.runCommand(command)
        println("Connected successfully to server.")
    } catch (me: MongoException) {
        System.err.println("An error occurred while attempting to run a command: $me")
    }
}
