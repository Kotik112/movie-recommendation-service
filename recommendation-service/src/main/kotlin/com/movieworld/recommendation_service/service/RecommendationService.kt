package com.movieworld.recommendation_service.service

import com.movieworld.recommendation_service.model.movie.MovieDto
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class RecommendationService(
    private val userClient: UserServiceClient,
    private val userProfileClient: UserProfileServiceClient,
    private val movieClient: MovieServiceClient,
) {
    private val logger = LoggerFactory.getLogger(RecommendationService::class.java)

    fun getRecommendations(userId: Long): List<MovieDto> {
        val user = userClient.getUserById(userId).also { logger.debug("User: {}", it) }
        val userProfile = userProfileClient.getUserProfileByEmail(user.email)
            .also { logger.debug("User Profile: {}", it) }
        val allMovies = movieClient.getAllMovies(). also { logger.debug("All Movies: {}", it) }

        val watchedMovies = userProfile.watchHistory?.map { it.movieId } ?: emptyList()
        val ratedMovies = userProfile.ratings?.map { it.movieId } ?: emptyList()

        val unwatchedMovies = allMovies.filter { it.id !in watchedMovies }
        val recommendedMovies = unwatchedMovies.filter { movie ->
            movie.genre in allMovies.filter { it.id in ratedMovies }.map { it.genre }
        }
        return recommendedMovies.ifEmpty { unwatchedMovies }
    }
}