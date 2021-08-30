package me.fdubuisson.leaderboard

import com.typesafe.config.ConfigFactory
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.DefaultHeaders
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.commandLineEnvironment
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
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

    // Declare Koin
    install(Koin) {
        modules(module)
    }


    // Routing section
    routing {
        get("/hello") {
            call.respondText("world")
        }
    }
}

fun main(args: Array<String>) {
    embeddedServer(Netty, commandLineEnvironment(args)).start()
}
