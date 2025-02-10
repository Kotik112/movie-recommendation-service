package com.movieworld.user_service.service.impl

//import com.movieworld.user_service.config.JwtUtil
//import org.springframework.security.authentication.AuthenticationManager
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
//import org.springframework.security.core.userdetails.UserDetails
//import org.springframework.security.core.userdetails.UserDetailsService
//import org.springframework.stereotype.Service

//@Service
//class AuthenticationService(
//    private val jwtUtil: JwtUtil,
//    private val userDetailsService: UserDetailsService,
//    private val authenticationManager: AuthenticationManager
//) {
//    fun authenticate(email: String, password: String): String {
//        authenticationManager.authenticate(UsernamePasswordAuthenticationToken(email, password))
//        val userDetails: UserDetails = userDetailsService.loadUserByUsername(email)
//        return jwtUtil.generateToken(userDetails.username)
//    }
//}