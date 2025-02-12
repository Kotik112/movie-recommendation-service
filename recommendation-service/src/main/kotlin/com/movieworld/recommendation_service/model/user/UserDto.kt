package com.movieworld.recommendation_service.model.user

data class UserDto(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    val watchHistory: MutableSet<WatchHistoryEntryDto>? = null,
    val ratings: MutableSet<RatingDto>? = null
)
