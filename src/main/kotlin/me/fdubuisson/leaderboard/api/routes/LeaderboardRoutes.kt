package me.fdubuisson.leaderboard.api.routes

import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.util.getValue
import me.fdubuisson.leaderboard.api.models.PlayerDto
import me.fdubuisson.leaderboard.domain.PlayerRepository
import me.fdubuisson.leaderboard.utils.Page
import org.koin.ktor.ext.inject

fun Application.leaderboardRoutes() {
    val playerRepository by inject<PlayerRepository>()

    routing {
        get("/leaderboard") {
            val page: Int by call.request.queryParameters
            val size: Int by call.request.queryParameters
            val start = page * size

            val players = playerRepository.findAllOrderByScoreDescAndIdAsc(start, size).mapIndexed { index, player ->
                PlayerDto(player.id.toString(), player.name, player.score, (start + index + 1).toLong())
            }
            val totalPlayers = playerRepository.countAll()
            call.respond(Page(players, totalPlayers))
        }
    }
}
