package com.movieworld.recommendation_service.model.user

import java.time.LocalDateTime

data class RatingDto(
    val id: Long,
    val movieId: Long,
    val ratingValue: Int,
    val ratedAt: LocalDateTime
) {
    init {
        require(ratingValue in 0..10) { "Rating must be between 0 and 10" }
    }
}
