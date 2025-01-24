package com.movieworld.user_service.service.impl

import com.movieworld.user_service.config.JwtUtil
import com.movieworld.user_service.repository.UserRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import kotlin.test.Test

class UserServiceImplTest {

    private lateinit var userService: UserServiceImpl
    private lateinit var userRepository: UserRepository
    private lateinit var jwtUtil: JwtUtil
    private lateinit var authenticationManager: AuthenticationManager
    private lateinit var userDetailsService: UserDetailsService
    private lateinit var passwordEncoder: PasswordEncoder

    @BeforeEach
    fun setUp() {
        userRepository = mock(UserRepository::class.java)
        jwtUtil = mock(JwtUtil::class.java)
        authenticationManager = mock(AuthenticationManager::class.java)
        userDetailsService = mock(UserDetailsService::class.java)
        passwordEncoder = mock(PasswordEncoder::class.java)
        userService = UserServiceImpl(userRepository, jwtUtil, authenticationManager, userDetailsService, passwordEncoder)
    }

    @Test
    fun `test authenticate`() {
        val email = "test@example.com"
        val password = "password"
        val userDetails = mock(UserDetails::class.java)

        `when`(userDetailsService.loadUserByUsername(email)).thenReturn(userDetails)
        `when`(userDetails.username).thenReturn(email)
        `when`(jwtUtil.generateToken(email)).thenReturn("token")

        val token = userService.authenticate(email, password)

        verify(authenticationManager).authenticate(UsernamePasswordAuthenticationToken(email, password))
        assertEquals("token", token)
    }
}