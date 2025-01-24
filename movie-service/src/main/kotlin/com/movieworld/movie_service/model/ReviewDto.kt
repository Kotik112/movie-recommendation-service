package com.movieworld.movie_service.model

data class ReviewDto(
    val movieId: Long,
    val userId: Long,
    val rating: Int,
    val comment: String
)
