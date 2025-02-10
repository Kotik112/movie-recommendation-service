package com.movieworld.user_service.service

import com.movieworld.user_service.model.RatingDto

fun interface RatingService {
    fun getRatingByUserProfileId(userProfileId: Long): List<RatingDto>
}