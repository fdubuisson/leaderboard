package me.fdubuisson.leaderboard.api

import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.routing
import me.fdubuisson.leaderboard.domain.PlayerRepository
import org.koin.ktor.ext.inject

fun Application.leaderboardRoutes() {
    val playerRepository by inject<PlayerRepository>()

    routing {
        get("/leaderboard") {
            val start = call.request.queryParameters["start"]?.toInt() ?: 0
            val count = call.request.queryParameters["count"]?.toInt() ?: 10

            val players = playerRepository.findAllOrderByScoreDescAndIdAsc(start, count)
            call.respond(players.mapIndexed { index, player ->
                PlayerDto(player.id.toString(), player.name, player.score, (start + index + 1).toLong())
            })
        }
    }
}
