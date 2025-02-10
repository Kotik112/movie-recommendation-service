package com.movieworld.user_service.service.impl

import com.movieworld.user_service.exception.UserAlreadyExistsException
import com.movieworld.user_service.exception.UserNotFoundException
import com.movieworld.user_service.model.User
import com.movieworld.user_service.model.UserDto
import com.movieworld.user_service.repository.UserRepository
import com.movieworld.user_service.service.UserService
import org.springframework.security.core.userdetails.User as SpringUser
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
): UserService {

    override fun createUser(user: UserDto): UserDto {
        if (userRepository.userExistsByEmail(user.email)) {
            throw UserAlreadyExistsException(message = "User with email ${user.email} already exists")
        }
        val userToSave = User(
            id = 0,
            firstName = user.firstName,
            lastName = user.lastName,
            email = user.email,
            password = passwordEncoder.encode(user.password),
        )
        return userRepository.save(userToSave).toDto()
    }

    override fun getUserById(userId: Long): UserDto {
        val user = userRepository.findByUserId(userId) ?: throw UserNotFoundException(message = "User with id: $userId not found")
        return user.toDto().copy(password = "")
    }

    override fun getUserByEmail(email: String): UserDto {
        val user = userRepository.findByEmail(email)
            ?: throw UserNotFoundException(message = "User with email $email, not found")
        return user.toDto().copy(password = "")
    }


    override fun updateUser(userToUpdate: UserDto): UserDto {
        val existingUser = userRepository.findByEmail(userToUpdate.email) ?: throw UserNotFoundException(message = "User not found")
        val updatedUser = existingUser.copy(
            firstName = userToUpdate.firstName,
            lastName = userToUpdate.lastName,
            email = userToUpdate.email,
            password = if (userToUpdate.password.isNotEmpty()) passwordEncoder.encode(userToUpdate.password) else existingUser.password
        )

        return userRepository.save(updatedUser).toDto()
    }

    override fun deleteUser(userId: Long) {
        userRepository.deleteById(userId)
    }

    override fun loadUserByUsername(email: String): UserDetails {
        val user = userRepository.findByEmail(email)
            ?: throw UsernameNotFoundException("User not found with email: $email")
        return SpringUser(
            user.email, user.password, emptyList()
        )
    }

    override fun existsByEmail(email: String): Boolean {
        return userRepository.userExistsByEmail(email)
    }
}