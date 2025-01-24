package com.movieworld.movie_service.exception

import com.movieworld.movie_service.model.ErrorDetail
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.util.*

@ControllerAdvice
class GlobalExceptionHandler: ResponseEntityExceptionHandler() {

    @ExceptionHandler(MovieAlreadyExistsException::class)
    fun handleUserNotFoundException(
        exception: MovieAlreadyExistsException,
        request: WebRequest
    ): ResponseEntity<ErrorDetail> {
        val errorDetail = ErrorDetail(
            timestamp = Date(),
            message = exception.message,
            details = request.getDescription(false)
        )
        return ResponseEntity.status(exception.getStatus()).body(errorDetail)
    }

    @ExceptionHandler(MovieNotFoundException::class)
    fun handleUserNotFoundException(
        exception: MovieNotFoundException,
        request: WebRequest
    ): ResponseEntity<ErrorDetail> {
        val errorDetail = ErrorDetail(
            timestamp = Date(),
            message = exception.message,
            details = request.getDescription(false)
        )
        return ResponseEntity.status(exception.getStatus()).body(errorDetail)
    }
}