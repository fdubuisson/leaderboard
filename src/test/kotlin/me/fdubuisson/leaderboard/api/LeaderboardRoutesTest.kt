package me.fdubuisson.leaderboard.api

import com.fasterxml.jackson.databind.ObjectMapper
import io.ktor.application.Application
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.withTestApplication
import kotlinx.coroutines.runBlocking
import me.fdubuisson.leaderboard.domain.Player
import me.fdubuisson.leaderboard.domain.PlayerRepository
import me.fdubuisson.leaderboard.main
import org.koin.java.KoinJavaComponent.inject
import kotlin.test.Test
import kotlin.test.assertEquals

class LeaderboardRoutesTest {
    private val objectMapper = ObjectMapper()

    @Test
    fun `get leaderboard`() {
        withTestApplication(Application::main) {
            val playerRepository by inject(PlayerRepository::class.java)
            val playersToRank = listOf(
                Player("player1").apply { score = 10 },
                Player("player2").apply { score = 0 },
                Player("player3").apply { score = 20 },
                Player("player4").apply { score = 15 }
            )

            runBlocking {
                playerRepository.clear()
                playersToRank.forEach { playerRepository.save(it) }
            }

            handleRequest(HttpMethod.Get, "/leaderboard?page=0&size=3").apply {
                assertEquals(HttpStatusCode.OK, response.status())

                val content = objectMapper.readTree(response.content!!)
                assertEquals(3, content.get("content").size())
                assertEquals(20, content.get("content")[0].get("score").asInt())
                assertEquals(15, content.get("content")[1].get("score").asInt())
                assertEquals(10, content.get("content")[2].get("score").asInt())
            }

            handleRequest(HttpMethod.Get, "/leaderboard?page=1&size=3").apply {
                assertEquals(HttpStatusCode.OK, response.status())

                val content = objectMapper.readTree(response.content!!)
                assertEquals(1, content.get("content").size())
                assertEquals(0, content.get("content")[0].get("score").asInt())
            }
        }
    }
}
