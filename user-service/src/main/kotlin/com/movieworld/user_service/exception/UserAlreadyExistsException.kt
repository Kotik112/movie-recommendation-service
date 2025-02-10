package com.movieworld.user_service.exception

import org.springframework.http.HttpStatus

class UserAlreadyExistsException(
    private val status: HttpStatus = HttpStatus.CONFLICT,
    override val message: String
): RuntimeException(message) {
    fun getStatus(): HttpStatus {
        return status
    }
}