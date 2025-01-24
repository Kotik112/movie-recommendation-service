package com.movieworld.movie_service.service.impl

import com.movieworld.movie_service.exception.MovieAlreadyExistsException
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

    override fun updateMovie(movieDto: MovieDto): MovieDto {
        TODO("Not yet implemented")
    }

    override fun deleteMovie(movieId: Long) {
        TODO("Not yet implemented")
    }

    override fun getMovie(movieId: Long): MovieDto {
        TODO("Not yet implemented")
    }

    override fun getMovies(): List<MovieDto> {
        TODO("Not yet implemented")
    }
}