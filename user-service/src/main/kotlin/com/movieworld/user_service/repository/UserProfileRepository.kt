package com.movieworld.user_service.repository

import com.movieworld.user_service.model.UserProfile
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserProfileRepository: JpaRepository<UserProfile, Long> {
    fun findByUserId(userId: Long): UserProfile?
    fun findByUserEmail(email: String): UserProfile?
    fun existsByUserId(userId: Long): Boolean
}