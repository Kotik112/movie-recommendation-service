package com.movieworld.user_service.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.ForeignKey
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "watch_history_entries")
class WatchHistoryEntry(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long,

    @Column(
        name = "movie_id",
        nullable = false
    )
    private val movieId: Long,

    @Column(
        name = "watched_at",
        nullable = false
    )
    private val watchedAt: LocalDateTime,

    @ManyToOne
    @JoinColumn(
        name = "user_profile_id",
        nullable = false,
        foreignKey = ForeignKey(name = "fk_watch_history_entries_user_profile_id")
    )
    private val userProfile: UserProfile
) {
    fun toDto(): WatchHistoryEntryDto {
        return WatchHistoryEntryDto(
            id = id,
            movieId = movieId,
            watchedAt = watchedAt,
        )
    }
}