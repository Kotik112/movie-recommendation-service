package com.movieworld.user_service.service.impl

import com.movieworld.user_service.exception.UserProfileNotFoundException
import com.movieworld.user_service.model.Rating
import com.movieworld.user_service.model.RatingDto
import com.movieworld.user_service.model.UserProfileDto
import com.movieworld.user_service.model.WatchHistoryEntry
import com.movieworld.user_service.model.WatchHistoryEntryDto
import com.movieworld.user_service.repository.UserProfileRepository
import com.movieworld.user_service.service.UserProfileService
import org.springframework.stereotype.Service

@Service
class UserProfileServiceImpl(
    private val userProfileRepository: UserProfileRepository
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

    override fun updateUserProfile(
        userId: Long,
        watchHistoryEntry: WatchHistoryEntryDto?,
        rating: RatingDto?
    ): UserProfileDto {
        val userProfile = userProfileRepository.findByUserId(userId)
            ?: throw UserProfileNotFoundException(message = "User Profile for user with id: $userId, not found")

        val updatedWatchHistory = watchHistoryEntry?.let {
            userProfile.watchHistory + WatchHistoryEntry(
                id = it.id,
                movieId = it.movieId,
                watchedOn = it.watchedAt,
                userProfile = userProfile,
            )
        }?.toMutableList() ?: userProfile.watchHistory

        val updatedRatings = rating?.let {
            userProfile.ratings + Rating(
                id = it.id,
                movieId = it.movieId,
                ratedAt = it.ratedAt,
                ratingValue = it.ratingValue,
                userProfile = userProfile
            )
        }?.toMutableList() ?: userProfile.ratings

        val updatedUserProfile = userProfile.copy(
            watchHistory = updatedWatchHistory,
            ratings = updatedRatings
        )

        return userProfileRepository.save(updatedUserProfile).toDto()
    }
}