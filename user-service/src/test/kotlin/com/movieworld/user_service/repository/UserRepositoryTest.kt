package com.movieworld.user_service.repository

import com.movieworld.movie_service.util.PostgresTestContainer
import com.movieworld.user_service.model.Role
import com.movieworld.user_service.model.User
import org.junit.jupiter.api.Assertions.assertEquals
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.annotation.DirtiesContext
import kotlin.test.Test

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class UserRepositoryTest: PostgresTestContainer() {

    @Autowired
    private lateinit var userRepository: UserRepository

    @Test
    fun `should return user by email`() {
        val user = User(
            email = "test@mail.com",
            password = "password",
            firstName = "John",
            lastName = "Doe",
            role = Role.USER
        )
        userRepository.save(user)

        val result = userRepository.findByEmail(user.email)

        assertEquals(user, result)
    }

    @Test
    fun `should return user by id`() {
        val user = User(
            email = "test@mail.com",
            password = "password",
            firstName = "John",
            lastName = "Doe",
            role = Role.USER
        )
        userRepository.save(user)

        val result = userRepository.findByUserId(1)

        assertEquals(user, result)
    }

    @Test
    fun `should return true if user exists by email`() {
        val user = User(
            email = "test@mail.com",
            password = "password",
            firstName = "John",
            lastName = "Doe",
            role = Role.USER
        )
        userRepository.save(user)

        val result = userRepository.userExistsByEmail(user.email)

        assertEquals(true, result)
    }
}