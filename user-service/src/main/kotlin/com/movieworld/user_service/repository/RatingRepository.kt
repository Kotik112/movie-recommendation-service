package com.movieworld.user_service.repository

import com.movieworld.user_service.model.Rating
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RatingRepository: JpaRepository<Rating, Long> {
    fun findByUserProfileId(userProfileId: Long): List<Rating>
}