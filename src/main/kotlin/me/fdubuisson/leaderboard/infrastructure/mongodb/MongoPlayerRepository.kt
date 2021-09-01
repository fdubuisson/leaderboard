package me.fdubuisson.leaderboard.infrastructure.mongodb

import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters
import me.fdubuisson.leaderboard.domain.Player
import me.fdubuisson.leaderboard.domain.PlayerRepository
import org.litote.kmongo.Id
import org.litote.kmongo.and
import org.litote.kmongo.ensureIndex
import org.litote.kmongo.eq
import org.litote.kmongo.findOneById
import org.litote.kmongo.gt
import org.litote.kmongo.lt
import org.litote.kmongo.or
import org.litote.kmongo.orderBy
import org.litote.kmongo.save

class MongoPlayerRepository(
    database: MongoDatabase
) : PlayerRepository {
    private val playerCollection: MongoCollection<Player> = database.getCollection("players", Player::class.java)

    init {
        // The player ID is used as a discriminator on score equality.
        playerCollection.ensureIndex(Player::score, Player::id)
    }

    override fun findById(id: Id<Player>): Player? {
        return playerCollection.findOneById(id)
    }

    override fun findAllOrderByScoreDescAndIdAsc(start: Int, count: Int): List<Player> {
        return playerCollection.find()
            .sort(orderBy(mapOf(Player::score to false, Player::id to true)))
            .skip(start)
            .limit(count)
            .toList()
    }

    override fun save(player: Player) {
        playerCollection.save(player)
    }

    override fun countByScoreGreaterThanAndIdGreaterThan(score: Int, id: Id<Player>): Long {
        return playerCollection.countDocuments(
            or(
                Player::score gt score,
                and(
                    Player::score eq score,
                    Player::id lt id
                )
            )
        )
    }

    override fun clear() {
        playerCollection.deleteMany(Filters.empty())
    }
}
