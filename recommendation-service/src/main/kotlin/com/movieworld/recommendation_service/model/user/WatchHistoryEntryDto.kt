package com.movieworld.recommendation_service.model.user

import java.time.LocalDateTime

data class WatchHistoryEntryDto(
    val id: Long,
    val movieId: Long,
    val watchedAt: LocalDateTime
)
