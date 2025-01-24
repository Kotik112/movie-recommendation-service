package com.movieworld.movie_service.repository

import com.movieworld.movie_service.model.Movie
import com.movieworld.movie_service.util.PostgresTestContainer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.time.LocalDate
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class MovieManagementRepositoryTest: PostgresTestContainer() {

    @Autowired
    private lateinit var movieManagementRepository: MovieManagementRepository

    @BeforeEach
    fun setup() {
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
        val movies = movieManagementRepository.findAllMovies()
        assertThat(movies).hasSize(2)
        assertEquals("The Shawshank Redemption", movies[0].title)
        assertEquals("The Godfather", movies[1].title)
    }

    @Test
    fun `Test findMovieByTitle`() {
        val movie = movieManagementRepository.findMovieByTitle("The Shawshank Redemption")
        assertEquals("The Shawshank Redemption", movie.title)
    }

    @Test
    fun `Test existsByTitle`() {
        val exists = movieManagementRepository.existsByTitle("The Shawshank Redemption")
        assertTrue(exists)
    }

    @Test
    fun `Test updateMovie`() {
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
        val deleted = movieManagementRepository.deleteByTitle("The Shawshank Redemption")
        assertEquals(1, deleted)

        val movies = movieManagementRepository.findAllMovies()
        assertThat(movies).hasSize(1)
    }
}