package com.movieworld.movie_service.service.impl

import com.movieworld.movie_service.exception.MovieAlreadyExistsException
import com.movieworld.movie_service.exception.MovieNotFoundException
import com.movieworld.movie_service.model.Movie
import com.movieworld.movie_service.model.MovieDto
import com.movieworld.movie_service.repository.MovieManagementRepository
import com.movieworld.movie_service.service.MovieManagementService
import com.movieworld.movie_service.util.TimeService
import org.springframework.stereotype.Service

@Service
class MovieManagementServiceImpl(
    val movieManagementRepository: MovieManagementRepository
): MovieManagementService {

    override fun findMovieById(id: Long): MovieDto {
        val movie = movieManagementRepository.findMovieById(id = id).orElseThrow {
            MovieNotFoundException(message = "Movie with id $id not found")
        }
        return movie.toDto()
    }

    override fun addMovie(movieDto: MovieDto): MovieDto {
        if (movieManagementRepository.existsByTitle(title =  movieDto.title)) {
            throw MovieAlreadyExistsException(message = "Movie with title ${movieDto.title} already exists")
        }
        val movie = Movie(
            title = movieDto.title,
            genre = movieDto.genre,
            releaseDate = movieDto.releaseDate,
            director = movieDto.director,
            description = movieDto.description
        )
        return movieManagementRepository.save(movie).toDto()
    }

    override fun updateMovie(movieId: Long, movieDto: MovieDto): Boolean {
        if (!movieManagementRepository.existsById(movieId)) {
            throw MovieNotFoundException(message = "Movie with id $movieId not found")
        }
        return movieManagementRepository.updateMovie(
            movieId = movieId,
            title = movieDto.title,
            genre = movieDto.genre,
            releaseDate = movieDto.releaseDate,
            director = movieDto.director,
            description = movieDto.description
        ) > 0
    }

    override fun deleteMovie(title: String): Boolean {
        if(!movieManagementRepository.existsByTitle(title)) {
            throw MovieNotFoundException(message = "Movie with title $title not found")
        }
        val rowsDeleted = movieManagementRepository.deleteByTitle(title = title)
        return rowsDeleted > 0
    }

    override fun getMovieByTitle(title: String): MovieDto {
        val movie = movieManagementRepository.findMovieByTitle(title = title).orElseThrow {
            MovieNotFoundException(message = "Movie with title $title not found")
        }
        return movie.toDto()
    }

    override fun getAllMovie(): List<MovieDto> {
        return movieManagementRepository.findAllMovies().map { it.toDto() }
    }

    override fun getAllMoviesByYear(yearAsInt: Int): List<MovieDto> {
        val year = TimeService.getTimeWindowForYear(yearAsInt = yearAsInt)
        return movieManagementRepository.findAllMoviesByYear(startDate = year.startDate, endDate = year.endDate)
            .map { it.toDto() }
    }

    override fun movieExistsById(movieId: Long): Boolean {
        return movieManagementRepository.existsById(movieId)
    }

    override fun movieExistsByTitle(title: String): Boolean {
        return movieManagementRepository.existsByTitle(title)
    }
}