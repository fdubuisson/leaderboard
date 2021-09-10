package me.fdubuisson.leaderboard.domain

import org.litote.kmongo.Id

interface PlayerRepository {

    suspend fun findById(id: Id<Player>): Player?

    suspend fun findAllOrderByScoreDescAndIdAsc(start: Int, count: Int): List<Player>

    suspend fun save(player: Player)

    suspend fun countByScoreGreaterThanAndIdGreaterThan(score: Int, id: Id<Player>): Long

    suspend fun countAll(): Long

    suspend fun clear()
}
