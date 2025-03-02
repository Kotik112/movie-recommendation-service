package com.movieworld.recommendation_service.controller

import com.movieworld.recommendation_service.service.RecommendationService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/recommendations")
@Suppress("unused")
class RecommendationController(
    private val recommendationService: RecommendationService
) {

    @GetMapping("/{userId}")
    fun getRecommendations(@PathVariable userId: Long) = recommendationService.getRecommendations(userId)
}