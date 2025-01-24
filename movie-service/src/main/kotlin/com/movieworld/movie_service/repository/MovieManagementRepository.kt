package com.movieworld.movie_service.repository

import com.movieworld.movie_service.model.Movie
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface MovieManagementRepository: JpaRepository<Movie, Long> {

    @Query("SELECT * FROM movie WHERE title = :title", nativeQuery = true)
    fun findMovieByTitle(@Param("title") title: String): Movie

    @Query("SELECT * FROM movie", nativeQuery = true)
    fun findAllMovies(): List<Movie>

    @Query("SELECT CASE WHEN COUNT(m) > 0 THEN TRUE ELSE FALSE END FROM Movie m WHERE m.title = :title")
    fun existsByTitle(@Param("title") title: String): Boolean

    @Transactional
    @Modifying
    @Query("UPDATE movie SET title = :title, genre = :genre, release_date = :releaseDate, director = :director, description = :description WHERE title = :currentTitle", nativeQuery = true)
    fun updateMovie(
        @Param("currentTitle") currentTitle: String,
        @Param("title") title: String,
        @Param("genre") genre: String,
        @Param("releaseDate") releaseDate: LocalDate,
        @Param("director") director: String,
        @Param("description") description: String
    ): Int

    @Transactional
    @Modifying
    @Query("DELETE FROM movie WHERE title = :title", nativeQuery = true)
    fun deleteByTitle(@Param("title") title: String): Int
}