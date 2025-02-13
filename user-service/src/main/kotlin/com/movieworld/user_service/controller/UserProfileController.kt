package com.movieworld.user_service.controller

import com.movieworld.user_service.model.RatingDto
import com.movieworld.user_service.model.UserProfileDto
import com.movieworld.user_service.model.WatchHistoryEntryDto
import com.movieworld.user_service.service.impl.UserProfileServiceImpl
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/userProfile")
@Suppress("unused")
class UserProfileController(
    private val userProfileService: UserProfileServiceImpl,
) {

    @GetMapping("/{id}")
    fun getUserProfileById(@PathVariable id: Long): UserProfileDto {
        return userProfileService.getUserProfileByUserId(id)
    }

    @GetMapping("/email/{email}")
    fun getUserProfileByEmail(@PathVariable email: String): UserProfileDto {
        return userProfileService.getUserProfileByEmail(email)
    }

    @PutMapping("/user/{userId}/watchHistory")
    fun updateWatchHistory(@PathVariable userId: Long, @RequestBody watchHistoryEntry: WatchHistoryEntryDto): UserProfileDto {
        return userProfileService.updateWatchHistory(userId, watchHistoryEntry)
    }

    @PutMapping("/user/{userId}/rating")
    fun updateRating(@PathVariable userId: Long, @RequestBody rating: RatingDto): UserProfileDto {
        return userProfileService.updateRating(userId, rating)
    }

    @DeleteMapping("/user/{userId}/watchHistory/{watchId}")
    fun deleteWatchHistoryEntry(@PathVariable userId: Long, @PathVariable watchId: Long): UserProfileDto {
        return userProfileService.deleteWatchHistoryEntry(userId, watchId)
    }

    @DeleteMapping("/user/{userId}/rating/{ratingId}")
    fun deleteRating(@PathVariable userId: Long, @PathVariable ratingId: Long): UserProfileDto {
        return userProfileService.deleteRating(userId, ratingId)
    }
}