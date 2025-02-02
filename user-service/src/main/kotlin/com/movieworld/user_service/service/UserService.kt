package com.movieworld.user_service.service

import com.movieworld.user_service.model.User
import com.movieworld.user_service.model.UserDto
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Repository

@Repository
interface UserService {
    fun createUser(user: UserDto): UserDto
    fun getUserById(id: Long): UserDto
    fun getUserByEmail(email: String): UserDto
    fun loadUserByUsername(email: String): UserDetails
//    fun updateUser(userToUpdate: UserDto): User
//    fun deleteUser(id: Long)
}