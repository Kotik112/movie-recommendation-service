package com.movieworld.user_service.repository

import com.movieworld.movie_service.util.PostgresTestContainer
import com.movieworld.user_service.model.User
import org.junit.jupiter.api.Assertions.assertEquals
import org.springframework.beans.factory.annotation.Autowired
import kotlin.test.Test

class UserRepositoryTest: PostgresTestContainer() {

    @Autowired
    private lateinit var userRepository: UserRepository

    @Test
    fun `should return user by email`() {
        val user = User(
            email = "test@mail.com",
            password = "password",
            firstName = "John",
            lastName = "Doe"
        )
        userRepository.save(user)

        val result = userRepository.findByEmail(user.email)

        assertEquals(null, result)
    }
}