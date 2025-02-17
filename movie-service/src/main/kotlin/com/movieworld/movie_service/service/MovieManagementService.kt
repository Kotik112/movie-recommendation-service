package com.movieworld.movie_service.service

import com.movieworld.movie_service.model.MovieDto

interface MovieManagementService {
    fun findMovieById(id: Long): MovieDto
    fun getMovieByTitle(title: String): MovieDto
    fun addMovie(movieDto: MovieDto): MovieDto
    fun updateMovie(movieId: Long, movieDto: MovieDto): Boolean
    fun deleteMovie(title: String): Boolean
    fun getAllMovie(): List<MovieDto>
    fun getAllMoviesByYear(year: Int): List<MovieDto>
    fun movieExistsById(movieId: Long): Boolean
    fun movieExistsByTitle(title: String): Boolean
}