package com.movieworld.user_service.service.impl

import com.movieworld.user_service.exception.MovieNotFoundException
import com.movieworld.user_service.exception.UserProfileNotFoundException
import com.movieworld.user_service.model.Rating
import com.movieworld.user_service.model.RatingDto
import com.movieworld.user_service.model.UserProfileDto
import com.movieworld.user_service.model.WatchHistoryEntry
import com.movieworld.user_service.model.WatchHistoryEntryDto
import com.movieworld.user_service.repository.UserProfileRepository
import com.movieworld.user_service.service.MovieServiceClient
import com.movieworld.user_service.service.UserProfileService
import org.springframework.stereotype.Service

@Service
class UserProfileServiceImpl(
    private val userProfileRepository: UserProfileRepository,
    private val movieService: MovieServiceClient
): UserProfileService {
    override fun getUserProfileByUserId(userId: Long): UserProfileDto {
        val userProfile = userProfileRepository.findByUserId(userId)
            ?: throw UserProfileNotFoundException(message = "User Profile for user with id: $userId, not found")
        return userProfile.toDto()
    }

    override fun getUserProfileByEmail(email: String): UserProfileDto {
        val userProfile = userProfileRepository.findByUserEmail(email)
            ?: throw UserProfileNotFoundException(message = "User profile for user with email: $email, not found")
        return userProfile.toDto()
    }

    override fun updateWatchHistory(userId: Long, watchHistoryEntry: WatchHistoryEntryDto): UserProfileDto {
        val userProfile = userProfileRepository.findByUserId(userId)
            ?: throw UserProfileNotFoundException(message = "User Profile for user with id: $userId, not found")
        if (!movieService.movieExistsById(watchHistoryEntry.movieId)) {
            throw MovieNotFoundException(message = "Movie with id: ${watchHistoryEntry.movieId}, not found")
        }
        val updatedWatchHistory = (userProfile.watchHistory + WatchHistoryEntry(
            movieId = watchHistoryEntry.movieId,
            watchedOn = watchHistoryEntry.watchedAt,
            userProfile = userProfile
        )).toMutableSet()
        val updatedUserProfile = userProfile.copy(watchHistory = updatedWatchHistory)
        return userProfileRepository.save(updatedUserProfile).toDto()
    }

    override fun updateRating(userId: Long, rating: RatingDto): UserProfileDto {
        val userProfile = userProfileRepository.findByUserId(userId)
            ?: throw UserProfileNotFoundException(message = "User Profile for user with id: $userId, not found")
        if (!movieService.movieExistsById(rating.movieId)) {
            throw MovieNotFoundException(message = "Movie with id: ${rating.movieId}, not found")
        }
        val updatedRatings = (userProfile.ratings + Rating(
            ratingValue = rating.ratingValue,
            ratedAt = rating.ratedAt,
            userProfile = userProfile,
            movieId = rating.movieId
        )).toMutableSet()
        val updatedUserProfile = userProfile.copy(ratings = updatedRatings)
        return userProfileRepository.save(updatedUserProfile).toDto()
    }

    fun deleteWatchHistoryEntry(userId: Long, watchHistoryEntryId: Long): UserProfileDto {
        val userProfile = userProfileRepository.findByUserId(userId)
            ?: throw UserProfileNotFoundException(message = "User Profile for user with id: $userId, not found")
        val updatedWatchHistory = userProfile.watchHistory
            .filter { it.id != watchHistoryEntryId }
            .toMutableSet()
        val updatedUserProfile = userProfile.copy(watchHistory = updatedWatchHistory)
        return userProfileRepository.save(updatedUserProfile).toDto()
    }

    fun deleteRating(userId: Long, ratingId: Long): UserProfileDto {
        val userProfile = userProfileRepository.findByUserId(userId)
            ?: throw UserProfileNotFoundException(message = "User Profile for user with id: $userId, not found")
        val updatedRatings = userProfile.ratings
            .filter { it.id != ratingId }
            .toMutableSet()
        val updatedUserProfile = userProfile.copy(ratings = updatedRatings)
        return userProfileRepository.save(updatedUserProfile).toDto()
    }
}