package com.movieworld.user_service.service.impl

import com.movieworld.user_service.exception.UserAlreadyExistsException
import com.movieworld.user_service.exception.UserNotFoundException
import com.movieworld.user_service.model.User
import com.movieworld.user_service.model.UserDto
import com.movieworld.user_service.model.UserProfile
import com.movieworld.user_service.repository.UserProfileRepository
import com.movieworld.user_service.repository.UserRepository
import com.movieworld.user_service.service.UserService
import org.slf4j.LoggerFactory
import org.springframework.security.core.userdetails.User as SpringUser
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val userProfileRepository: UserProfileRepository
): UserService {

    companion object {
        private val logger = LoggerFactory.getLogger(UserServiceImpl::class.java)
    }

    /**
    * Create a new user in the users table, also create a user profile for the user with
    * empty watch history and ratings.
    *
    * @param user: UserDto - the user to create
    * @return UserDto - the created user
     */
    override fun createUser(user: UserDto): UserDto {
        if (userRepository.userExistsByEmail(email = user.email)) {
            logger.warn("User already exists")
            throw UserAlreadyExistsException(message = "User with email ${user.email} already exists")
        }
        val userToSave = User(
            firstName = user.firstName,
            lastName = user.lastName,
            email = user.email,
            password = passwordEncoder.encode(user.password),
            role = user.role
        )
        val savedUser = userRepository.save(userToSave)
        val userProfile = UserProfile(
            user = savedUser,
            watchHistory = mutableSetOf(),
            ratings = mutableSetOf()
        )
        logger.info("Saving user to the database")
        userProfileRepository.save(userProfile)
        return savedUser.toDto()
    }

    /**
    * Get a user by their id
    *
    * @param userId: Long - the id of the user to get
    * @return UserDto - the user with the given id
     */
    override fun getUserById(userId: Long): UserDto {
        val user = userRepository.findByUserId(userId)
            ?: run {
                logger.warn("User with id: $userId not found")
                throw UserNotFoundException(message = "User with id: $userId not found")
            }
        logger.debug("User with id: $userId found")
        return user.toDto().copy(password = "")
    }

    /**
    * Get a user by their email
    *
    * @param email: String - the email of the user to get
    * @return UserDto - the user with the given email
     */
    override fun getUserByEmail(email: String): UserDto {
        val user = userRepository.findByEmail(email)
            ?: run {
                logUserNotFound(email)
                throw UserNotFoundException(message = "User with email: $email not found")
            }
        return user.toDto().copy(password = "")
    }

    /**
    * Update a user in the users table
    *
    * @param userToUpdate: UserDto - the user to update
    * @return UserDto - the updated user
     */
    override fun updateUser(userToUpdate: UserDto): UserDto {
        val existingUser = userRepository.findByEmail(userToUpdate.email)
            ?: run {
                logUserNotFound(userToUpdate.email)
                throw UserNotFoundException(message = "User with email: ${userToUpdate.email} not found")
            }
        val updatedUser = existingUser.copy(
            firstName = userToUpdate.firstName,
            lastName = userToUpdate.lastName,
            email = userToUpdate.email,
            password = if (userToUpdate.password.isNotEmpty()) passwordEncoder.encode(userToUpdate.password) else existingUser.password
        )

        return userRepository.save(updatedUser).toDto().copy(password = "")
    }

    /**
    * Delete a user from the users table
    *
    * @param userId: Long - the id of the user to delete
     */
    override fun deleteUser(userId: Long) {
        logger.debug("Deleting user with id: $userId")
        userRepository.deleteById(userId)
    }

    /**
     * Load a user by their email for Spring Security
     *
     * @param email: String - the email of the user to load
     * @return UserDetails - the user with the given email
     */
    override fun loadUserByUsername(email: String): UserDetails {
        val user = userRepository.findByEmail(email = email)
            ?: run {
                logUserNotFound(email)
                throw UsernameNotFoundException("User not found with email: $email")
            }
        return SpringUser(
            user.email, user.password, emptyList()
        )
    }

    /**
     * Check if a user with the given email exists
     *
     * @param email: String - the email to check
     * @return Boolean - true if a user with the given email exists, false otherwise
     */
    override fun existsByEmail(email: String): Boolean {
        return userRepository.userExistsByEmail(email = email)
    }

    private fun logUserNotFound(email: String) {
        logger.warn("User with email: $email not found")
    }
}