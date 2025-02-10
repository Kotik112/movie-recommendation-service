package com.movieworld.user_service.exception

import org.springframework.http.HttpStatus

data class UserProfileNotFoundException(
    val status: HttpStatus = HttpStatus.NOT_FOUND,
    override val message: String
): RuntimeException(message)
