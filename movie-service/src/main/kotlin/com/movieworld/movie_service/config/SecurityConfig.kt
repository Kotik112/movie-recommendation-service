package com.movieworld.movie_service.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
class SecurityConfig {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() } // Disable CSRF for development
            .authorizeHttpRequests { auth ->
                auth.requestMatchers(
                    "/v3/api-docs/**",    // Allow access to OpenAPI docs
                    "/swagger-ui/**",     // Allow access to Swagger UI
                    "/swagger-ui.html" ,
                    "/api/v1/users",
                ).permitAll()
                auth.anyRequest().permitAll() // Allow all requests (adjust as needed)
            }
            .cors { it.configurationSource(corsConfigurationSource()) } // Set the custom CORS configuration

        return http.build()
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()
        configuration.allowedOrigins = listOf("http://127.0.0.1:5500") // Allow frontend requests
        configuration.allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "OPTIONS")
        configuration.allowedHeaders = listOf("Authorization", "Content-Type")
        configuration.allowCredentials = true // Allow cookies (if needed)

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration) // Apply to all endpoints
        return source
    }
}
