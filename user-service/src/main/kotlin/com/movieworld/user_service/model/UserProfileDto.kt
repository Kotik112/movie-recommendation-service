package com.movieworld.user_service.model

data class UserProfileDto(
    val id: Long,
    val user: UserDto,
    val watchHistory: MutableList<WatchHistoryEntryDto>,
    val ratings: MutableList<RatingDto>
)