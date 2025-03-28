package com.movieworld.user_service.controller

import com.movieworld.user_service.model.LoginDto
import com.movieworld.user_service.service.authentication.AuthenticationService
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/auth")
@Suppress("unused")
class AuthController(
    private val authenticationService: AuthenticationService
) {
    @PostMapping("/login")
    fun login(@RequestBody request: LoginDto): ResponseEntity<Map<String, String>> {
        val token = authenticationService.authenticate(request)
        return ResponseEntity.ok(mapOf("token" to token))
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/logout")
    fun logout(): String {
        return "You have been logged out."
    }
}