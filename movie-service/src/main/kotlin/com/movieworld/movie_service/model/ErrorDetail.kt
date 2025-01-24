package com.movieworld.movie_service.model

import java.util.Date

data class ErrorDetail(
    val timestamp: Date,
    val message: String,
    val details: String
)
