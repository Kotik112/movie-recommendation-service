package com.movieworld.user_service.model

data class UserProfileDto(
    val id: Long,
    val user: UserDto,
    val watchHistory: MutableList<WatchHistoryEntryDto>? = null,
    val ratings: MutableList<RatingDto>? = null
)