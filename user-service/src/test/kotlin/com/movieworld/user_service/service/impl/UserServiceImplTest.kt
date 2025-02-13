package com.movieworld.user_service.service.impl

import com.movieworld.user_service.model.User
import com.movieworld.user_service.model.UserDto
import com.movieworld.user_service.model.UserProfile
import com.movieworld.user_service.repository.UserProfileRepository
import com.movieworld.user_service.repository.UserRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.mockito.Mockito.any
import org.mockito.Mockito.anyString
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.springframework.security.crypto.password.PasswordEncoder
import kotlin.test.Test


class UserServiceImplTest {

    private lateinit var userRepository: UserRepository
    private lateinit var userProfileRepository: UserProfileRepository
    private lateinit var passwordEncoder: PasswordEncoder


    @BeforeEach
    fun setUp() {
        userRepository = mock(UserRepository::class.java)
        userProfileRepository = mock(UserProfileRepository::class.java)
        passwordEncoder = mock(PasswordEncoder::class.java)
        `when`(passwordEncoder.encode(anyString())).thenReturn("encodedPassword")
    }

    @Test
    fun `Test createUser`() {
        val userService = UserServiceImpl(
            userRepository = userRepository,
            passwordEncoder = passwordEncoder,
            userProfileRepository = userProfileRepository
        )
        val user = User(
            id = 1,
            firstName = "John",
            lastName = "Doe",
            email = "test@mail.com",
            password = "password",
        )
        val userProfile = UserProfile(
            id = 1,
            user = user,
            watchHistory = mutableSetOf(),
            ratings = mutableSetOf()
        )
        // Expected UserDto
        val userDto = UserDto(
            id = 1,
            firstName = "John",
            lastName = "Doe",
            email = "test@mail.com",
            password = "password",
        )
        `when`(userRepository.userExistsByEmail("test@mail.com")).thenReturn(false)
        `when`(userRepository.save(any<User>())).thenReturn(user)
        `when`(userProfileRepository.save(any())).thenReturn(userProfile)

        val result = userService.createUser(userDto)
        verify(passwordEncoder, times(1)).encode(anyString())
        assertEquals(userDto, result)
    }

    @Test
    fun `Test getUserById`() {
        val userService = UserServiceImpl(
            userRepository = userRepository,
            passwordEncoder = passwordEncoder,
            userProfileRepository = userProfileRepository
        )
        val user = User(
            id = 1,
            firstName = "John",
            lastName = "Doe",
            email = "test@mail.com",
            password = "password",
        )

        `when`(userRepository.findByUserId(1)).thenReturn(user)

        // Expected UserDto
        val userDto = UserDto(
            id = 1,
            firstName = "John",
            lastName = "Doe",
            email = "test@mail.com",
            password = "",
        )

        val result = userService.getUserById(1)
        assertEquals(userDto, result)
    }

    @Test
    fun `Test getUserByEmail`() {
        val userService = UserServiceImpl(
            userRepository = userRepository,
            passwordEncoder = passwordEncoder,
            userProfileRepository = userProfileRepository
        )
        val user = User(
            id = 1,
            firstName = "John",
            lastName = "Doe",
            email = "test@mail.com",
            password = "password",
        )

        // Expected UserDto
        val userDto = UserDto(
            id = 1,
            firstName = "John",
            lastName = "Doe",
            email = "test@mail.com",
            password = "",
        )

        `when`(userRepository.findByEmail(anyString())).thenReturn(user)

        val result = userService.getUserByEmail(user.email)
        assertEquals(userDto, result)
    }

    @Test
    fun `Test updateUser`() {
        val userService = UserServiceImpl(
            userRepository = userRepository,
            passwordEncoder = passwordEncoder,
            userProfileRepository = userProfileRepository
        )
        val user = User(
            id = 1,
            firstName = "John",
            lastName = "Doe",
            email = "test@mail.com",
            password = "password",
        )
        val updatedUser = user.copy(
            firstName = "Jane",
            lastName = "Doe",
            email = "newEmail@gmail.com",
            password = "newPassword"
        )

        // Expected UserDto
        val userDto = UserDto(
            id = 1,
            firstName = "Jane",
            lastName = "Doe",
            email = "newEmail@gmail.com",
            password = "newPassword",
        )

        `when`(userRepository.findByEmail(anyString())).thenReturn(user)
        `when`(userRepository.save(any())).thenReturn(updatedUser)

        val result = userService.updateUser(userDto)
        assertEquals(userDto, result)
    }

    @Test
    fun `Test deleteUser`() {
        val userService = UserServiceImpl(
            userRepository = userRepository,
            passwordEncoder = passwordEncoder,
            userProfileRepository = userProfileRepository
        )
        val user = User(
            id = 1,
            firstName = "John",
            lastName = "Doe",
            email = "test@mail.com",
            password = "password",
        )

        `when`(userRepository.findByEmail(anyString())).thenReturn(user)

        userService.deleteUser(1)

        verify(userRepository, times(1)).deleteById(1)
    }

    @Test
    fun `Test existsByEmail`() {
        val userService = UserServiceImpl(
            userRepository = userRepository,
            passwordEncoder = passwordEncoder,
            userProfileRepository = userProfileRepository
        )
        `when`(userRepository.userExistsByEmail(anyString())).thenReturn(true)
        val result = userService.existsByEmail("test@mail.com")
        assertEquals(true, result)
    }
}