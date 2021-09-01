package me.fdubuisson.leaderboard.domain

import org.litote.kmongo.Id

interface PlayerRepository {

    fun findById(id: Id<Player>): Player?

    fun findAllOrderByScoreDescAndIdAsc(start: Int, count: Int): List<Player>

    fun save(player: Player)

    fun countByScoreGreaterThanAndIdGreaterThan(score: Int, id: Id<Player>): Long

    fun clear()
}
