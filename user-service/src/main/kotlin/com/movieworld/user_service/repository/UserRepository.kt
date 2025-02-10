package com.movieworld.user_service.repository

import com.movieworld.user_service.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {

    @Query("SELECT u FROM User u JOIN FETCH u.userProfile up LEFT JOIN FETCH up.watchHistory LEFT JOIN FETCH up.ratings WHERE u.id = :id")
    fun findByUserId(@Param("id") id: Long): User?

    @Query("SELECT u FROM User u JOIN FETCH u.userProfile up LEFT JOIN FETCH up.watchHistory LEFT JOIN FETCH up.ratings WHERE u.email = :email")
    fun findByEmail(@Param("email") email: String): User?

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.email = :email")
    fun userExistsByEmail(email: String): Boolean

}