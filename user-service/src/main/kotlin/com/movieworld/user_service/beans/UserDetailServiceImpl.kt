package com.movieworld.user_service.beans

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UserDetailServiceImpl {

    @Suppress("unused")
    @Bean
    fun userDetailsService(): UserDetailServiceImpl {
        return UserDetailServiceImpl()
    }
}