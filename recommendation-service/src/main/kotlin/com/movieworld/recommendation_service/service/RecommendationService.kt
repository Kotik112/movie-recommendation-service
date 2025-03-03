package com.movieworld.recommendation_service.service

import com.movieworld.recommendation_service.model.movie.MovieDto
import org.springframework.stereotype.Service

@Service
class RecommendationService(
    private val userClient: UserServiceClient,
    private val userProfileClient: UserProfileServiceClient,
    private val movieClient: MovieServiceClient,
) {
    fun getRecommendations(userId: Long): List<MovieDto> {
        val user = userClient.getUserById(userId)
        val userProfile = userProfileClient.getUserProfileByEmail(user.email)
        val allMovies = movieClient.getAllMovies()

        userProfile.watchHistory?.let { watchHistory ->
            val watchedMovieIds = watchHistory.map { it.movieId }
            return allMovies.filter { it.id !in watchedMovieIds }
        }

        return allMovies
    }
}