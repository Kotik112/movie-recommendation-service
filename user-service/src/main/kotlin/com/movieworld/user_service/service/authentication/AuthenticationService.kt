package com.movieworld.user_service.service.authentication

import com.movieworld.user_service.config.JwtUtil
import com.movieworld.user_service.model.LoginDto
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class AuthenticationService(
    private val jwtUtil: JwtUtil,
    private val userDetailsService: UserDetailsService,
    private val authenticationManager: AuthenticationManager
) {
    companion object {
        private val logger = LoggerFactory.getLogger(AuthenticationService::class.java)
    }

    fun authenticate(loginDto: LoginDto): String {
        val email: String = loginDto.email
        val password: String = loginDto.password
        return try {
            logger.info("Attempting to authenticate user with email: $email")
            authenticationManager.authenticate(UsernamePasswordAuthenticationToken(email, password))
            val userDetails: UserDetails = userDetailsService.loadUserByUsername(email)
            jwtUtil.generateToken(userDetails)
        }
        catch (_: Exception) {
            logger.error("Authentication failed for user with email: $email")
            "Invalid credentials. Please try again."
        }
    }
}