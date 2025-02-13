package com.movieworld.user_service.model.movies

import java.time.LocalDate

data class MovieDto(
    val id: Long = 0,
    val title: String,
    val genre: String,
    val releaseDate: LocalDate,
    val director: String,
    val description: String
)