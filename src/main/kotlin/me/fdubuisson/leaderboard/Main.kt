package me.fdubuisson.leaderboard

import com.typesafe.config.ConfigFactory
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.CORS
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.jackson.jackson
import io.ktor.routing.routing
import io.ktor.server.engine.commandLineEnvironment
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import me.fdubuisson.leaderboard.api.routes.leaderboardRoutes
import me.fdubuisson.leaderboard.api.routes.playerRoutes
import me.fdubuisson.leaderboard.domain.PlayerRepository
import me.fdubuisson.leaderboard.infrastructure.mongodb.DatabaseLoader
import me.fdubuisson.leaderboard.infrastructure.mongodb.MongoPlayerRepository
import org.koin.dsl.module
import org.koin.ktor.ext.Koin

fun Application.main() {
    @Suppress("USELESS_CAST")
    val module = module {
        single { ConfigFactory.load() }
        single { DatabaseLoader(get()).load() }
        single { MongoPlayerRepository(get()) as PlayerRepository }
    }

    // Install Ktor features
    install(DefaultHeaders)
    install(CallLogging)
    install(CORS) {
        anyHost()
    }
    install(ContentNegotiation) {
        jackson()
    }

    // Declare Koin
    install(Koin) {
        modules(module)
    }

    routing {
        playerRoutes()
        leaderboardRoutes()
    }
}

fun main(args: Array<String>) {
    embeddedServer(Netty, commandLineEnvironment(args)).start()
}
