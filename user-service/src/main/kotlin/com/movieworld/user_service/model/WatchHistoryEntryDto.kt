package com.movieworld.user_service.model

import java.time.LocalDateTime

data class WatchHistoryEntryDto(
    val id: Long,
    val movieId: Long,
    val watchedAt: LocalDateTime
)
