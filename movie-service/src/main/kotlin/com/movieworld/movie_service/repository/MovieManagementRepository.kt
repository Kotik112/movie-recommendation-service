package com.movieworld.movie_service.repository

import com.movieworld.movie_service.model.Movie
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDate
import java.util.Optional

@Repository
interface MovieManagementRepository: JpaRepository<Movie, Long> {

    @Query("SELECT * FROM movie WHERE id = :id", nativeQuery = true)
    fun findMovieById(@Param("id") id: Long): Optional<Movie>

    @Query("SELECT * FROM movie WHERE title = :title", nativeQuery = true)
    fun findMovieByTitle(@Param("title") title: String): Optional<Movie>

    @Query("SELECT * FROM movie", nativeQuery = true)
    fun findAllMovies(): List<Movie>

    @Query("SELECT * FROM movie WHERE release_date BETWEEN :startDate AND :endDate", nativeQuery = true)
    fun findAllMoviesByYear(@Param("startDate") startDate: LocalDate, @Param("endDate") endDate: LocalDate): List<Movie>

    @Query("SELECT CASE WHEN COUNT(m) > 0 THEN TRUE ELSE FALSE END FROM Movie m WHERE m.id = :id")
    fun existsById(@Param("id") title: String): Boolean

    @Query("SELECT CASE WHEN COUNT(m) > 0 THEN TRUE ELSE FALSE END FROM Movie m WHERE m.title = :title")
    fun existsByTitle(@Param("title") title: String): Boolean

    @Transactional
    @Modifying
    @Query("UPDATE movie SET title = :title, genre = :genre, release_date = :releaseDate, director = :director, description = :description WHERE id = :movieId", nativeQuery = true)
    fun updateMovie(
        @Param("movieId") movieId: Long,
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