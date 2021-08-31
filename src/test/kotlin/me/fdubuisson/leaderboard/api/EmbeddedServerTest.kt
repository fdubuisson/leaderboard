package me.fdubuisson.leaderboard.api

import com.fasterxml.jackson.databind.ObjectMapper
import io.ktor.application.Application
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.setBody
import io.ktor.server.testing.withTestApplication
import me.fdubuisson.leaderboard.domain.Player
import me.fdubuisson.leaderboard.domain.PlayerRepository
import me.fdubuisson.leaderboard.main
import org.koin.java.KoinJavaComponent.inject
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class EmbeddedServerTest {
    private val objectMapper = ObjectMapper()

    @Test
    fun `create and get player`() {
        withTestApplication(Application::main) {
            var playerId: String?

            handleRequest(HttpMethod.Post, "/players") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(
                    objectMapper.writeValueAsString(
                        mapOf("name" to "player1")
                    )
                )
            }.apply {
                assertEquals(HttpStatusCode.OK, response.status())

                val player = objectMapper.readTree(response.content!!)
                assertNotNull(player["id"].asText())
                assertEquals("player1", player["name"].asText())

                playerId = player["id"].asText()
            }

            handleRequest(HttpMethod.Get, "/players/$playerId").apply {
                assertEquals(HttpStatusCode.OK, response.status())

                val player = objectMapper.readTree(response.content!!)
                assertEquals(playerId, player["id"].asText())
                assertEquals("player1", player["name"].asText())
            }
        }
    }

    @Test
    fun `update player score`() {
        withTestApplication(Application::main) {
            val playerRepository by inject(PlayerRepository::class.java)
            val player1 = Player("player1")
            playerRepository.save(player1)

            handleRequest(HttpMethod.Put, "/players/${player1.id}/score") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(
                    objectMapper.writeValueAsString(
                        mapOf("score" to 42)
                    )
                )
            }.apply {
                assertEquals(HttpStatusCode.OK, response.status())

                val player = objectMapper.readTree(response.content!!)
                assertEquals(42, player["score"].asInt())
            }
        }
    }
}
