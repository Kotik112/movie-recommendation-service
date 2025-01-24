package com.movieworld.movie_service.model

import java.time.LocalDate

data class MovieDto(
    val title: String,
    val genre: String,
    val releaseDate: LocalDate,
    val director: String,
    val description: String
)