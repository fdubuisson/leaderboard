package me.fdubuisson.leaderboard.api.routes

import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.routing
import me.fdubuisson.leaderboard.api.models.PlayerDto
import me.fdubuisson.leaderboard.domain.PlayerRepository
import me.fdubuisson.leaderboard.utils.Page
import org.koin.ktor.ext.inject

fun Application.leaderboardRoutes() {
    val playerRepository by inject<PlayerRepository>()

    routing {
        get("/leaderboard") {
            val page = call.request.queryParameters["page"]?.toInt() ?: 0
            val size = call.request.queryParameters["size"]?.toInt() ?: 10
            val start = page * size

            val players = playerRepository.findAllOrderByScoreDescAndIdAsc(start, size).mapIndexed { index, player ->
                PlayerDto(player.id.toString(), player.name, player.score, (start + index + 1).toLong())
            }
            val totalPlayers = playerRepository.countAll()
            call.respond(Page(players, totalPlayers))
        }
    }
}
