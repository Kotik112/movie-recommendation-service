package com.movieworld.movie_service.service

import com.movieworld.movie_service.exception.MovieAlreadyExistsException
import com.movieworld.movie_service.exception.MovieNotFoundException
import com.movieworld.movie_service.model.Movie
import com.movieworld.movie_service.model.MovieDto
import com.movieworld.movie_service.repository.MovieManagementRepository
import com.movieworld.movie_service.service.impl.MovieManagementServiceImpl
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.any
import org.mockito.Mockito.spy
import org.mockito.MockitoAnnotations
import org.mockito.Mockito.`when`
import java.time.LocalDate
import java.util.*
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class MovieManagementServiceTest {

    @Mock
    private lateinit var movieManagementRepository: MovieManagementRepository

    @InjectMocks
    private lateinit var movieManagementService: MovieManagementServiceImpl

    @BeforeEach
    fun setup() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `addMovie should return movieDto`() {
        `when`(movieManagementRepository.existsByTitle("The Matrix")).thenReturn(false)

        val movie = spy(Movie(
            title = "The Matrix",
            genre = "Action",
            releaseDate = LocalDate.of(1999, 3, 31),
            director = "Lana Wachowski, Lilly Wachowski",
            description = "A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against its controllers."
        ))
        `when`(movieManagementRepository.save(any(Movie::class.java))).thenReturn(movie)

        val movieDto = MovieDto(
            title = "The Matrix",
            genre = "Action",
            releaseDate = LocalDate.of(1999, 3, 31),
            director = "Lana Wachowski, Lilly Wachowski",
            description = "A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against its controllers."
        )

        `when`(movie.toDto()).thenReturn(movieDto)

        val actual = movieManagementService.addMovie(movieDto)

        assertEquals(movieDto, actual)
    }

    @Test
    fun `test addMovie should throw MovieNotFoundException`() {
        `when`(movieManagementRepository.existsByTitle("The Matrix")).thenReturn(true)

        val movieDto = MovieDto(
            title = "The Matrix",
            genre = "Action",
            releaseDate = LocalDate.of(1999, 3, 31),
            director = "Lana Wachowski, Lilly Wachowski",
            description = "A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against its controllers."
        )

        assertThrows<MovieAlreadyExistsException> {
            movieManagementService.addMovie(movieDto)
        }
    }

    @Test
    fun `test findMovieById`() {
        val movieToFind = Movie(
            title = "The Matrix",
            genre = "Action",
            releaseDate = LocalDate.of(1999, 3, 31),
            director = "Lana Wachowski, Lilly Wachowski",
            description = "A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against its controllers."
        )
        val movie = Optional.of(movieToFind)
        `when`(movieManagementRepository.findMovieById(1)).thenReturn(movie)

        val actual = movieManagementService.findMovieById(1)
        assertEquals(movieToFind.toDto(), actual)
    }

    @Test
    fun `test findMovieById should throw MovieNotFoundException`() {
        val movie = Optional.empty<Movie>()
        `when`(movieManagementRepository.findMovieById(1)).thenReturn(movie)

        assertThrows<MovieNotFoundException> {
            movieManagementService.findMovieById(1)
        }
    }

    @Test
    fun `test updateMovie`() {
        val movie = Movie(
            title = "The Matrix",
            genre = "Action",
            releaseDate = LocalDate.of(1999, 3, 31),
            director = "Lana Wachowski, Lilly Wachowski",
            description = "A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against its controllers."
        )

        `when`(movieManagementRepository.existsById(1)).thenReturn(true)
        `when`(movieManagementRepository.updateMovie(
            movieId = 1,
            title = "The Matrix",
            genre = "Action",
            releaseDate = LocalDate.of(1999, 3, 31),
            director = "Lana Wachowski, Lilly Wachowski",
            description = "A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against its controllers."
        )).thenReturn(1)

        assertTrue { movieManagementService.updateMovie(1, movie.toDto()) }
    }

    @Test
    fun `test updateMovie should throw MovieNotFoundException`() {
        val movie = Movie(
            title = "The Matrix",
            genre = "Action",
            releaseDate = LocalDate.of(1999, 3, 31),
            director = "Lana Wachowski, Lilly Wachowski",
            description = "A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against its controllers."
        )

        `when`(movieManagementRepository.existsByTitle("The Matrix")).thenReturn(false)

        assertThrows<MovieNotFoundException> {
            movieManagementService.updateMovie(1, movie.toDto())
        }
    }

    @Test
    fun `test deleteMovie`() {
        `when`(movieManagementRepository.existsByTitle("The Matrix")).thenReturn(true)
        `when`(movieManagementRepository.deleteByTitle("The Matrix")).thenReturn(1)

        val actual = movieManagementService.deleteMovie("The Matrix")
        assertEquals(true, actual)
    }

    @Test
    fun `test deleteMovie should throw MovieNotFoundException`() {
        `when`(movieManagementRepository.existsByTitle("The Matrix")).thenReturn(false)

        assertThrows<MovieNotFoundException> {
            movieManagementService.deleteMovie("The Matrix")
        }
    }

    @Test
    fun `test getMovie`() {
        val movieToFind = Movie(
            title = "The Matrix",
            genre = "Action",
            releaseDate = LocalDate.of(1999, 3, 31),
            director = "Lana Wachowski, Lilly Wachowski",
            description = "A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against its controllers."
        )
        val movie = Optional.of(movieToFind)
        `when`(movieManagementRepository.findMovieByTitle("The Matrix")).thenReturn(movie)

        val actual = movieManagementService.getMovieByTitle("The Matrix")
        assertEquals(movieToFind.toDto(), actual)
    }

    @Test
    fun `test getMovie should throw MovieNotFoundException`() {
        val movie = Optional.empty<Movie>()
        `when`(movieManagementRepository.findMovieByTitle("The Matrix")).thenReturn(movie)

        assertThrows<MovieNotFoundException> {
            movieManagementService.getMovieByTitle("The Matrix")
        }
    }

    @Test
    fun `Test getAllMovies`() {
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

        `when`(movieManagementRepository.findAllMovies()).thenReturn(listOf(movie1, movie2))

        val movies = movieManagementService.getAllMovie()
        assertEquals(2, movies.size)
        assertEquals("The Shawshank Redemption", movies[0].title)
        assertEquals("The Godfather", movies[1].title)
    }

    @Test
    fun `Test getAllMovies should return empty list`() {
        `when`(movieManagementRepository.findAllMovies()).thenReturn(emptyList())

        val movies = movieManagementService.getAllMovie()
        assertEquals(0, movies.size)
    }

    @Test
    fun `Test getAllMoviesByYear`() {
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

        `when`(movieManagementRepository.findAllMoviesByYear(LocalDate.of(1994, 1, 1), LocalDate.of(1994, 12, 31))).thenReturn(listOf(movie1, movie2))

        val movies = movieManagementService.getAllMoviesByYear(1994)
        assertEquals(2, movies.size)
        assertEquals("The Shawshank Redemption", movies[0].title)
        assertEquals("The Godfather", movies[1].title)
    }

    @Test
    fun `test movieExistsById`() {
        `when`(movieManagementRepository.existsById(1)).thenReturn(true)

        assertTrue {
            movieManagementService.movieExistsById(1)
        }
    }

    @Test
    fun `test movieExistsById should return false`() {
        `when`(movieManagementRepository.existsById(1)).thenReturn(false)

        assertFalse {
            movieManagementService.movieExistsById(1)
        }
    }

    @Test
    fun `test movieExistsByTitle`() {
        `when`(movieManagementRepository.existsByTitle("The Matrix")).thenReturn(true)

        assertTrue {
            movieManagementService.movieExistsByTitle("The Matrix")
        }
    }

    @Test
    fun `test movieExistsByTitle should return false`() {
        `when`(movieManagementRepository.existsByTitle("The Matrix")).thenReturn(false)

        assertFalse {
            movieManagementService.movieExistsByTitle("The Matrix")
        }
    }
}