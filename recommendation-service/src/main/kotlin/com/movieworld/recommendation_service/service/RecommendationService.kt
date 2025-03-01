package com.movieworld.recommendation_service.service

import org.springframework.stereotype.Service

@Service
class RecommendationService(
    private val userClient: UserServiceClient,
    private val movieClient: MovieServiceClient,
) {
    fun getRecommendations(userId: Long): List<String> {
        val user = userClient.getUserById(userId)
        val allMovies = movieClient.getAllMovies()

        //val watchHistory = userClient.
        return emptyList()
    }
}