package com.movieworld.user_service.repository

import com.movieworld.user_service.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {
    @Query("INSERT INTO users (first_name, last_name, email, password) VALUES (:firstName, :lastName, :email, :password) RETURNING *", nativeQuery = true)
    fun createUser(
        @Param("firstName") firstName: String,
        @Param("lastName") lastName: String,
        @Param("email") email: String,
        @Param("password") password: String
    ): User

    @Query("SELECT * FROM users WHERE id = :id", nativeQuery = true)
    fun findByUserId(@Param("id") id: Long): User?

    @Query("SELECT * FROM users WHERE email = :email", nativeQuery = true)
    fun findByEmail(@Param("email") email: String): User?

}