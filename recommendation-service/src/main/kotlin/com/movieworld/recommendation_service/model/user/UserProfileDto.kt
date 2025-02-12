package com.movieworld.recommendation_service.model.user

data class UserProfileDto(
    val id: Long,
    val user: UserDto,
    val watchHistory: MutableList<WatchHistoryEntryDto>? = null,
    val ratings: MutableList<RatingDto>? = null
)