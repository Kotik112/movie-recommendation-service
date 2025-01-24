package com.movieworld.movie_service.repository

import com.movieworld.movie_service.model.Movie
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface MovieManagementRepository: JpaRepository<Movie, Long> {

    @Query("SELECT * FROM movies WHERE title = :title", nativeQuery = true)
    fun findMovieByTitle(@Param("title") title: String): Movie

    @Query("SELECT CASE WHEN COUNT(m) > 0 THEN TRUE ELSE FALSE END FROM Movie m WHERE m.title = :title")
    fun existsByTitle(@Param("title") title: String): Boolean
}