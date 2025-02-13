package com.movieworld.user_service.service.authentication

import com.movieworld.user_service.config.JwtUtil
import com.movieworld.user_service.model.LoginDto
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
    fun authenticate(loginDto: LoginDto): String {
        val email: String = loginDto.email
        val password: String = loginDto.password
        return try {
            authenticationManager.authenticate(UsernamePasswordAuthenticationToken(email, password))
            val userDetails: UserDetails = userDetailsService.loadUserByUsername(email)
            jwtUtil.generateToken(userDetails.username)
        }
        catch (e: Exception) {
            "Invalid credentials. Please try again."
        }
    }
}