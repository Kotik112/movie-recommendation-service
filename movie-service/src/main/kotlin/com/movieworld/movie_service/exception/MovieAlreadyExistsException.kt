package com.movieworld.movie_service.exception

import org.springframework.http.HttpStatus

class MovieAlreadyExistsException(
    private val status: HttpStatus = HttpStatus.CONFLICT,
    override val message: String
): RuntimeException(message) {
    fun getStatus(): HttpStatus {
        return status
    }
}