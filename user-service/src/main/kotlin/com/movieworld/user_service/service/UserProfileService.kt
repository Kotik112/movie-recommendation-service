package com.movieworld.user_service.service

import com.movieworld.user_service.model.RatingDto
import com.movieworld.user_service.model.UserProfileDto
import com.movieworld.user_service.model.WatchHistoryEntryDto

interface UserProfileService {
    fun getUserProfileByUserId(userId: Long): UserProfileDto
    fun getUserProfileByEmail(email: String): UserProfileDto
    fun updateUserProfile(userId: Long, watchHistoryEntry: WatchHistoryEntryDto? = null, rating: RatingDto? = null): UserProfileDto
}