package me.fdubuisson.leaderboard.domain

import org.litote.kmongo.Id

interface PlayerRepository {

    fun findById(id: Id<Player>): Player?

    fun save(player: Player)

    fun countByScoreGreaterThan(score: Int): Long
    
    fun clear()
}
