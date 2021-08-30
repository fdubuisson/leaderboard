package me.fdubuisson.leaderboard.domain

import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id
import org.litote.kmongo.newId

class Player(
    name: String
) {
    @BsonId
    val id: Id<Player> = newId() // TODO: custom id ?

    val name: String = name

    var score: Int = 0
}
