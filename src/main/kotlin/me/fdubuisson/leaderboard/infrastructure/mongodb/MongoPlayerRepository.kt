package me.fdubuisson.leaderboard.infrastructure.mongodb

import com.mongodb.client.model.Filters
import kotlinx.coroutines.runBlocking
import me.fdubuisson.leaderboard.domain.Player
import me.fdubuisson.leaderboard.domain.PlayerRepository
import org.litote.kmongo.Id
import org.litote.kmongo.and
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import org.litote.kmongo.gt
import org.litote.kmongo.lt
import org.litote.kmongo.or
import org.litote.kmongo.orderBy

class MongoPlayerRepository(
    database: CoroutineDatabase
) : PlayerRepository {
    private val playerCollection = database.getCollection<Player>("players")

    init {
        runBlocking {
            // The player ID is used as a discriminator on score equality.
            playerCollection.ensureIndex(Player::score, Player::id)
        }
    }

    override suspend fun findById(id: Id<Player>): Player? {
        return playerCollection.findOneById(id)
    }

    override suspend fun findAllOrderByScoreDescAndIdAsc(start: Int, count: Int): List<Player> {
        return playerCollection.find()
            .sort(orderBy(mapOf(Player::score to false, Player::id to true)))
            .skip(start)
            .limit(count)
            .toList()
    }

    override suspend fun save(player: Player) {
        playerCollection.save(player)
    }

    override suspend fun countByScoreGreaterThanAndIdGreaterThan(score: Int, id: Id<Player>): Long {
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

    override suspend fun countAll(): Long {
        return playerCollection.countDocuments()
    }

    override suspend fun clear() {
        playerCollection.deleteMany(Filters.empty())
    }
}
