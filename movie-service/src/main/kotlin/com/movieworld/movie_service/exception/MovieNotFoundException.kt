package com.movieworld.movie_service.exception

import org.springframework.http.HttpStatus

class MovieNotFoundException(
    private val status: HttpStatus = HttpStatus.NOT_FOUND,
    override val message: String
): RuntimeException(message) {
    fun getStatus(): HttpStatus {
        return status
    }
}