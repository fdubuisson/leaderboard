package me.fdubuisson.leaderboard.api

import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.routing
import me.fdubuisson.leaderboard.domain.Player
import me.fdubuisson.leaderboard.domain.PlayerRepository
import org.koin.ktor.ext.inject
import org.litote.kmongo.id.WrappedObjectId

fun Application.playerRoutes() {
    val playerRepository by inject<PlayerRepository>()

    routing {
        post("/players") {
            val input = call.receive<CreatePlayer>()
            val player = Player(input.name)
            playerRepository.save(player)
            call.respond(player.toDto())
        }

        get("/players/{playerId}") {
            val playerId = WrappedObjectId<Player>(call.parameters["playerId"]!!)
            val player = playerRepository.findById(playerId)
            if (player != null) {
                call.respond(player.toDto())
            } else {
                call.respond(HttpStatusCode.NotFound)
            }
        }
    }
}

data class CreatePlayer(
    val name: String
)

data class PlayerDto(
    val id: String,
    val name: String,
    val score: Int,
    val rank: Int
)

private fun Player.toDto() = PlayerDto(id.toString(), name, score, 0)
