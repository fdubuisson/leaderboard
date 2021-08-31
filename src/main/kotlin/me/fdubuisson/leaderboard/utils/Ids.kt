package me.fdubuisson.leaderboard.utils

import org.litote.kmongo.id.WrappedObjectId

fun <T> String.asId() = WrappedObjectId<T>(this)
