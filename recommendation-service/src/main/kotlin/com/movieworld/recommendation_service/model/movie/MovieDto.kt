package com.movieworld.recommendation_service.model.movie

import java.time.LocalDate

data class MovieDto(
    val id: Long = 0,
    val title: String,
    val genre: String,
    val releaseDate: LocalDate,
    val director: String,
    val description: String
)