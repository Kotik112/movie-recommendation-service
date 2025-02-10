package com.movieworld.user_service.service.impl

import com.movieworld.user_service.model.RatingDto
import com.movieworld.user_service.repository.RatingRepository
import com.movieworld.user_service.service.RatingService
import org.springframework.stereotype.Service

@Service
class RatingServiceImpl(
    private val ratingRepository: RatingRepository
): RatingService {
    override fun getRatingByUserProfileId(userProfileId: Long): List<RatingDto> {
        return ratingRepository.findByUserProfileId(userProfileId).map { it.toDto() }
    }

}