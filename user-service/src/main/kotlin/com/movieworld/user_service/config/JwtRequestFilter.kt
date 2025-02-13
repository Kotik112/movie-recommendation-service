package com.movieworld.user_service.config

import com.movieworld.user_service.service.UserService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.web.filter.OncePerRequestFilter

class JwtRequestFilter(
    private val jwtUtil: JwtUtil,
    private val userService: UserService
): OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authorizationHeader = request.getHeader("Authorization")

        var email: String? = null
        var jwt: String? = null

        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7)
            email = jwtUtil.extractEmail(jwt)
        }

        if (email != null && SecurityContextHolder.getContext().authentication == null) {
            val userDetails = userService.loadUserByUsername(email)

            if (jwtUtil.validateToken(jwt!!, userDetails.username)) {
                val role = jwtUtil.extractRole(jwt)
                val authority = listOf(SimpleGrantedAuthority(role))
                val usernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(
                    /* principal = */ userDetails,
                    /* credentials = */ null,
                    /* authorities = */ authority
                )
                usernamePasswordAuthenticationToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = usernamePasswordAuthenticationToken
            }
        }
        filterChain.doFilter(request, response)
    }
}