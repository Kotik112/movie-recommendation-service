package com.movieworld.movie_service.service.impl

import com.movieworld.movie_service.exception.MovieAlreadyExistsException
import com.movieworld.movie_service.exception.MovieNotFoundException
import com.movieworld.movie_service.model.Movie
import com.movieworld.movie_service.model.MovieDto
import com.movieworld.movie_service.repository.MovieManagementRepository
import com.movieworld.movie_service.service.MovieManagementService
import org.springframework.stereotype.Service

@Service
class MovieManagementServiceImpl(
    val movieManagementRepository: MovieManagementRepository
): MovieManagementService {
    override fun addMovie(movieDto: MovieDto): MovieDto {
        if (!movieManagementRepository.existsByTitle(movieDto.title)) {
            val movie = Movie(
                title = movieDto.title,
                genre = movieDto.genre,
                releaseDate = movieDto.releaseDate,
                director = movieDto.director,
                description = movieDto.description
            )
            return movieManagementRepository.save(movie).toDto()
        } else {
            throw MovieAlreadyExistsException(message = "Movie with title ${movieDto.title} already exists")
        }
    }

    override fun updateMovie(currentTitle: String, movieDto: MovieDto): Boolean {
        if (movieManagementRepository.existsByTitle(currentTitle)) {
            return movieManagementRepository.updateMovie(
                currentTitle = currentTitle,
                title = movieDto.title,
                genre = movieDto.genre,
                releaseDate = movieDto.releaseDate,
                director = movieDto.director,
                description = movieDto.description
            ) > 0
        } else {
            throw MovieNotFoundException(message = "Movie with title $currentTitle not found")
        }
    }

    override fun deleteMovie(title: String): Boolean {
        val rowsDeleted = movieManagementRepository.deleteByTitle(title = title)
        return rowsDeleted > 0
    }

    override fun getMovie(title: String): MovieDto {
        val movie = movieManagementRepository.findMovieByTitle(title = title)
        return movie.toDto()
    }

    override fun getMovies(): List<MovieDto> {
        return movieManagementRepository.findAllMovies().map { it.toDto() }
    }
}