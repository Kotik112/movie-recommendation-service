package com.movieworld.movie_service.service

import com.movieworld.movie_service.model.MovieDto

interface MovieManagementService {
    fun findMovieById(id: Long): MovieDto
    fun addMovie(movieDto: MovieDto): MovieDto
    fun updateMovie(currentTitle: String, movieDto: MovieDto): Boolean
    fun deleteMovie(title: String): Boolean
    fun getMovie(title: String): MovieDto
    fun getMovies(): List<MovieDto>
}