package com.movieworld.user_service.controller

import com.movieworld.user_service.model.UserDto
import com.movieworld.user_service.service.impl.UserServiceImpl
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/users")
class UserController(
    private val userService: UserServiceImpl
) {

    @PostMapping("/create")
    fun createUser(@RequestBody user: UserDto): UserDto {
        return userService.createUser(user = user)
    }

    @GetMapping("/{id}")
    fun getUserById(@PathVariable("id") id: Long): UserDto {
        return userService.getUserById(id)
    }

    @GetMapping("/email/{email}")
    fun getUserByEmail(@PathVariable("email") email: String): UserDto {
        return userService.getUserByEmail(email)
    }

    @GetMapping("/{id}/preferences")
    fun getUserPreferences(@PathVariable("id") id: Long): UserDto {
        return userService.getUserPreferences(id)
    }
}