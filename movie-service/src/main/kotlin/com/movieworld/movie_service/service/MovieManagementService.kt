package com.movieworld.movie_service.service

import com.movieworld.movie_service.model.MovieDto

interface MovieManagementService {

    fun addMovie(movieDto: MovieDto): MovieDto
    fun updateMovie(movieDto: MovieDto): MovieDto
    fun deleteMovie(movieId: Long)
    fun getMovie(movieId: Long): MovieDto
    fun getMovies(): List<MovieDto>
}