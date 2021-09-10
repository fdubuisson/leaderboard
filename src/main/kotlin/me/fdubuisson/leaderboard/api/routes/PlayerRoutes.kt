package me.fdubuisson.leaderboard.api.routes

import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.features.NotFoundException
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.put
import io.ktor.routing.routing
import me.fdubuisson.leaderboard.api.models.PlayerDto
import me.fdubuisson.leaderboard.domain.Player
import me.fdubuisson.leaderboard.domain.PlayerRepository
import me.fdubuisson.leaderboard.utils.asId
import org.koin.ktor.ext.inject

fun Application.playerRoutes() {
    val playerRepository by inject<PlayerRepository>()

    routing {
        post("/players") {
            val input = call.receive<CreatePlayer>()
            val player = Player(input.name)

            playerRepository.save(player)

            call.respond(player.toDto(playerRepository))
        }

        get("/players/{playerId}") {
            val playerId = call.parameters["playerId"]!!.asId<Player>()
            val player = playerRepository.findById(playerId) ?: throw NotFoundException()

            call.respond(player.toDto(playerRepository))
        }

        put("/players/{playerId}/score") {
            val playerId = call.parameters["playerId"]!!.asId<Player>()
            val input = call.receive<UpdateScore>()

            val player = playerRepository.findById(playerId) ?: throw NotFoundException()
            player.score = input.score
            playerRepository.save(player)

            call.respond(player.toDto(playerRepository))
        }
    }
}

private data class CreatePlayer(
    val name: String
)

private data class UpdateScore(
    val score: Int
)

private suspend fun Player.toDto(playerRepository: PlayerRepository) = PlayerDto(id.toString(), name, score, playerRepository.countByScoreGreaterThanAndIdGreaterThan(score, id) + 1)
