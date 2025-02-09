package com.movieworld.recommendation_service.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.LocalDateTime

@Entity
data class UserInteraction(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    val userId: Long,

    @Column(nullable = false)
    val movieId: Long,

    // For example: "WATCHED", "LIKED", "RATED"
    @Column(nullable = false)
    val interactionType: String,

    @Column(nullable = false)
    val timestamp: LocalDateTime
)
