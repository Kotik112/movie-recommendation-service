package com.movieworld.user_service.controller

import com.movieworld.user_service.model.UserDto
import com.movieworld.user_service.service.UserService
import com.movieworld.user_service.service.authentication.AuthenticationService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/users")
@Suppress("unused")
class UserController(
    private val userService: UserService,
    private val authenticationService: AuthenticationService
) {
    @PostMapping("/create")
    fun createUser(@RequestBody user: UserDto): UserDto {
        return userService.createUser(user = user)
    }

    @GetMapping("/login")
    fun login(@RequestBody userDto: UserDto): String {
        return authenticationService.authenticate(userDto = userDto)
    }

    @GetMapping("/{id}")
    fun getUserById(@PathVariable("id") id: Long): UserDto {
        return userService.getUserById(userId = id)
    }

    @GetMapping("/email/{email}")
    fun getUserByEmail(@PathVariable("email") email: String): UserDto {
        return userService.getUserByEmail(email = email)
    }

    @PutMapping("/update")
    fun updateUser(@RequestBody userToUpdate: UserDto): UserDto {
        return userService.updateUser(userToUpdate = userToUpdate)
    }

    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable("id") id: Long) {
        userService.deleteUser(userId = id)
    }
}