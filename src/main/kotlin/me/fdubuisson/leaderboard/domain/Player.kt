package me.fdubuisson.leaderboard.domain

import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id
import org.litote.kmongo.newId

class Player(
    @BsonId
    val id: Id<Player> = newId(), // TODO: custom Id ?
    val name: String, // TODO: VO
    var score: Int = 0
)
