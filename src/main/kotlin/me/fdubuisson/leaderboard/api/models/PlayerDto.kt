package me.fdubuisson.leaderboard.api.models

data class PlayerDto(
    val id: String,
    val name: String,
    val score: Int,
    val rank: Long
)
