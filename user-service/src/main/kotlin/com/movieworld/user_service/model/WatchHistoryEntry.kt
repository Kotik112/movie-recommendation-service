package com.movieworld.user_service.model

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "watch_history_entries")
data class WatchHistoryEntry(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_profile_id", nullable = false)
    val userProfile: UserProfile,

    val movieId: Long,

    val watchedOn: LocalDateTime
) {
    fun toDto(): WatchHistoryEntryDto {
        return WatchHistoryEntryDto(
            id = id,
            movieId = movieId,
            watchedAt = watchedOn
        )
    }
}