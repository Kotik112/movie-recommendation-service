package com.movieworld.user_service.service

import com.movieworld.user_service.model.RatingDto
import com.movieworld.user_service.model.UserProfileDto
import com.movieworld.user_service.model.WatchHistoryEntryDto

interface UserProfileService {
    fun getUserProfileByUserId(userId: Long): UserProfileDto
    fun getUserProfileByEmail(email: String): UserProfileDto
    fun updateWatchHistory(userId: Long, watchHistoryEntry: WatchHistoryEntryDto): UserProfileDto
    fun updateRating(userId: Long, rating: RatingDto): UserProfileDto
}