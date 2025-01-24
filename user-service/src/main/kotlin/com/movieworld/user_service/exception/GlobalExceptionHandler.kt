package com.movieworld.user_service.exception

import com.movieworld.user_service.model.ErrorDetail
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.util.Date

@ControllerAdvice
class GlobalExceptionHandler: ResponseEntityExceptionHandler() {

    @ExceptionHandler(UserNotFoundException::class)
    fun handleUserNotFoundException(
        exception: UserNotFoundException,
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