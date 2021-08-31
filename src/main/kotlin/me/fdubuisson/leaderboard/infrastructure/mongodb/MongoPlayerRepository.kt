package me.fdubuisson.leaderboard.infrastructure.mongodb

import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters
import me.fdubuisson.leaderboard.domain.Player
import me.fdubuisson.leaderboard.domain.PlayerRepository
import org.litote.kmongo.Id
import org.litote.kmongo.findOneById
import org.litote.kmongo.gt
import org.litote.kmongo.save

class MongoPlayerRepository(
    database: MongoDatabase
) : PlayerRepository {
    private val playerCollection: MongoCollection<Player> = database.getCollection("players", Player::class.java)

    override fun findById(id: Id<Player>): Player? {
        return playerCollection.findOneById(id)
    }

    override fun save(player: Player) {
        playerCollection.save(player)
    }

    override fun countByScoreGreaterThan(score: Int): Long {
        return playerCollection.countDocuments(Player::score gt score)
    }

    override fun clear() {
        playerCollection.deleteMany(Filters.empty())
    }
}
