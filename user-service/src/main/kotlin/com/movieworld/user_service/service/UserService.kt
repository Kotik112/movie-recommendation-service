package com.movieworld.user_service.service

import com.movieworld.user_service.model.UserDto
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Repository

@Repository
interface UserService {
    fun createUser(user: UserDto): UserDto
    fun getUserById(userId: Long): UserDto
    fun getUserByEmail(email: String): UserDto
    fun updateUser(userToUpdate: UserDto): UserDto
    fun deleteUser(userId: Long)
    fun loadUserByUsername(email: String): UserDetails
    fun existsByEmail(email: String): Boolean
}