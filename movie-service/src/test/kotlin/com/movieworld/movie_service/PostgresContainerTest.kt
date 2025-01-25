package com.movieworld.movie_service

import com.movieworld.movie_service.util.PostgresTestContainer
import java.sql.DriverManager
import kotlin.test.Test
import kotlin.test.assertTrue

class PostgresContainerTest: PostgresTestContainer() {
    @Test
    fun `test container start successfully`() {
        assertTrue(postgresqlContainer.isRunning)
    }

    @Test
    fun `test database connection`() {
        val jdbcUrl = postgresqlContainer.jdbcUrl
        val username = postgresqlContainer.username
        val password = postgresqlContainer.password

        DriverManager.getConnection(jdbcUrl, username, password).use { connection ->
            assertTrue(connection.isValid(2))
        }
    }
}