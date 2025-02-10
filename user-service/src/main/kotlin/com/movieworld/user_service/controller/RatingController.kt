package com.movieworld.user_service.controller

import com.movieworld.user_service.model.RatingDto
import com.movieworld.user_service.service.RatingService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/rating")
@Suppress("unused")
class RatingController(private val ratingService: RatingService) {

    @GetMapping("/{userProfileId}")
    fun getRatingByUserProfileId(userProfileId: Long): List<RatingDto> {
        return ratingService.getRatingByUserProfileId(userProfileId)
    }
}