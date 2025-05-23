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
import com.movieworld.user_service.service.authentication.AuthenticationService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class UserProfileServiceImpl(
    private val userProfileRepository: UserProfileRepository,
    private val movieService: MovieServiceClient,
    private val authenticationService: AuthenticationService
): UserProfileService {

    companion object {
        private val logger = LoggerFactory.getLogger(UserProfileServiceImpl::class.java)
    }

    override fun getUserProfileByUserId(userId: Long): UserProfileDto {
        val userProfile = userProfileRepository.findByUserId(userId)
            ?: run {
                logUserProfileNotFound(userId)
                throw UserProfileNotFoundException(message = "User Profile for user with id: $userId, not found")
            }
        return userProfile.toDto()
    }

    override fun getUserProfileByEmail(email: String): UserProfileDto {
        val userProfile = userProfileRepository.findByUserEmail(email)
            ?: throw UserProfileNotFoundException(message = "User profile for user with email: $email, not found")
        return userProfile.toDto()
    }

    override fun updateWatchHistory(userId: Long, watchHistoryEntry: WatchHistoryEntryDto): UserProfileDto {
        if (userId != authenticationService.getAuthenticatedUserId()) {
            // TODO: Change to a better exception
            throw UserProfileNotFoundException(message = "Not authorized to update watch history for user with id: $userId")
        }

        val userProfile = userProfileRepository.findByUserId(userId)
            ?: run {
                logUserProfileNotFound(userId)
                throw UserProfileNotFoundException(message = "User Profile for user with id: $userId, not found")
            }
        if (!movieService.movieExistsById(watchHistoryEntry.movieId)) {
            throw MovieNotFoundException(message = "Movie with id: ${watchHistoryEntry.movieId}, not found")
        }
        val updatedWatchHistory = userProfile.watchHistory.map {
            if (it.movieId == watchHistoryEntry.movieId) {
                logger.debug("User has already watched movie with id: ${watchHistoryEntry.movieId}, updating watchedOn date")
                it.copy(watchedOn = watchHistoryEntry.watchedAt)
            }
            else
                it
        }.toMutableSet()

        if (updatedWatchHistory.none { it.movieId == watchHistoryEntry.movieId }) {
            updatedWatchHistory.add(WatchHistoryEntry(
                userProfile = userProfile,
                movieId = watchHistoryEntry.movieId,
                watchedOn = watchHistoryEntry.watchedAt
            ))
        }

        val updatedUserProfile = userProfile.copy(watchHistory = updatedWatchHistory)
        return userProfileRepository.save(updatedUserProfile).toDto()
    }

    override fun updateRating(userId: Long, rating: RatingDto): UserProfileDto {
        if (userId != authenticationService.getAuthenticatedUserId()) {
            // TODO: Change to a better exception
            throw UserProfileNotFoundException(message = "Not authorized to update watch history for user with id: $userId")
        }

        val userProfile = userProfileRepository.findByUserId(userId)
            ?: run {
                logUserProfileNotFound(userId)
                throw UserProfileNotFoundException(message = "User Profile for user with id: $userId, not found")
            }
        if (!movieService.movieExistsById(rating.movieId)) {
            throw MovieNotFoundException(message = "Movie with id: ${rating.movieId}, not found")
        }
        val updatedRatings = userProfile.ratings.map {
            if (it.movieId == rating.movieId) {
                logger.debug("User has already rated movie with id: ${rating.movieId}, updating rating value")
                it.copy(
                    ratingValue = rating.ratingValue,
                    ratedAt = rating.ratedAt
                )
            }
            else
                it
        }.toMutableSet()

        if (updatedRatings.none { it.movieId == rating.movieId }) {
            updatedRatings.add(Rating(
                userProfile = userProfile,
                movieId = rating.movieId,
                ratingValue = rating.ratingValue
            ))
        }

        val updatedUserProfile = userProfile.copy(ratings = updatedRatings)
        return userProfileRepository.save(updatedUserProfile).toDto()
    }

    fun deleteWatchHistoryEntry(userId: Long, watchHistoryEntryId: Long): UserProfileDto {
        val userProfile = userProfileRepository.findByUserId(userId)
            ?: run {
                logUserProfileNotFound(userId)
                throw UserProfileNotFoundException(message = "User Profile for user with id: $userId, not found")
            }
        val updatedWatchHistory = userProfile.watchHistory
            .filter { it.id != watchHistoryEntryId }
            .toMutableSet()
        val updatedUserProfile = userProfile.copy(watchHistory = updatedWatchHistory)
        return userProfileRepository.save(updatedUserProfile).toDto()
    }

    fun deleteRating(userId: Long, ratingId: Long): UserProfileDto {
        val userProfile = userProfileRepository.findByUserId(userId)
            ?: run {
                logUserProfileNotFound(userId)
                throw UserProfileNotFoundException(message = "User Profile for user with id: $userId, not found")
            }
        val updatedRatings = userProfile.ratings
            .filter { it.id != ratingId }
            .toMutableSet()
        val updatedUserProfile = userProfile.copy(ratings = updatedRatings)
        return userProfileRepository.save(updatedUserProfile).toDto()
    }

    private fun logUserProfileNotFound(userId: Long) {
        logger.error("User Profile for user with id: $userId, not found")
    }
}