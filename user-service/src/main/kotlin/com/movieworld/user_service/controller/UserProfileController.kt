package com.movieworld.user_service.controller

import com.movieworld.user_service.model.UserProfileDto
import com.movieworld.user_service.service.impl.UserProfileServiceImpl
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/userProfile")
@Suppress("unused")
class UserProfileController(
    private val userProfileService: UserProfileServiceImpl
) {

    @GetMapping("/{id}")
    fun getUserProfileById(@PathVariable id: Long): UserProfileDto {
        return userProfileService.getUserProfileByUserId(id)
    }

    @GetMapping("/email/{email}")
    fun getUserProfileByEmail(@PathVariable email: String): UserProfileDto {
        return userProfileService.getUserProfileByEmail(email)
    }
}