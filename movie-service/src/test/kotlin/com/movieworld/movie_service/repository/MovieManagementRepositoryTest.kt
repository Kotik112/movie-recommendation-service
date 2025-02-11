package com.movieworld.movie_service.repository

import com.movieworld.movie_service.model.Movie
import com.movieworld.movie_service.util.PostgresTestContainer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.time.LocalDate
import kotlin.test.assertEquals
import kotlin.test.assertTrue


class MovieManagementRepositoryTest: PostgresTestContainer() {

    @Autowired
    private lateinit var movieManagementRepository: MovieManagementRepository

    private fun saveMovies() {
        movieManagementRepository.deleteAll()
        val movie1 = Movie(
            title = "The Shawshank Redemption",
            genre = "Drama",
            releaseDate = LocalDate.of(1994, 10, 14),
            director = "Frank Darabont",
            description = "Two imprisoned"
        )
        val movie2 = Movie(
            title = "The Godfather",
            genre = "Crime",
            releaseDate = LocalDate.of(1972, 3, 24),
            director = "Francis Ford Coppola",
            description = "The aging patriarch"
        )
        movieManagementRepository.saveAll(listOf(movie1, movie2))
    }

    @Test
    fun `test getAllMovies`() {
        saveMovies()
        val movies = movieManagementRepository.findAllMovies()
        assertThat(movies).hasSize(2)
        assertEquals("The Shawshank Redemption", movies[0].title)
        assertEquals("The Godfather", movies[1].title)
    }

    @Test
    fun `Test findMovieById not found`() {
        val movie = movieManagementRepository.findMovieById(1)
        assertTrue(movie.isEmpty)
    }

    @Test
    fun `Test findMovieById`() {
        saveMovies()
        val allMovies = movieManagementRepository.findAllMovies()
        val movie1 = movieManagementRepository.findMovieById(allMovies[0].id)
        assertTrue(movie1.isPresent)
        assertEquals("The Shawshank Redemption", movie1.get().title)

        val movie2 = movieManagementRepository.findMovieById(allMovies[1].id)
        assertTrue(movie2.isPresent)
        assertEquals("The Godfather", movie2.get().title)
    }

    @Test
    fun `Test findMovieByTitle`() {
        saveMovies()
        val movie = movieManagementRepository.findMovieByTitle("The Shawshank Redemption")
        assertEquals("The Shawshank Redemption", movie.get().title)
    }

    @Test
    fun `Test existsByTitle`() {
        val exists = movieManagementRepository.existsByTitle("The Shawshank Redemption")
        assertTrue(exists)
    }

    @Test
    fun `Test updateMovie`() {
        saveMovies()
        val updated = movieManagementRepository.updateMovie(
            currentTitle = "The Shawshank Redemption",
            title = "The Shawshank Redemption",
            genre = "Drama",
            releaseDate = LocalDate.of(1994, 10, 14),
            director = "New Director",
            description = "New Description"
        )
        assertEquals(1, updated)
    }

    @Test
    fun `Test deleteByTitle`() {
        saveMovies()
        val deleted = movieManagementRepository.deleteByTitle("The Shawshank Redemption")
        assertEquals(1, deleted)

        val movies = movieManagementRepository.findAllMovies()
        assertThat(movies).hasSize(1)
        assertEquals("The Godfather", movies[0].title)
    }
}