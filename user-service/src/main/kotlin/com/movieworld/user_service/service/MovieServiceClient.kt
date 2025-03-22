package com.movieworld.user_service.service

import com.movieworld.user_service.model.movies.MovieDto
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(name = "movie-service", url = "\${url.movie-service}")
interface MovieServiceClient {

    @GetMapping("/{id}")
    fun getMovieById(@PathVariable id: Long): MovieDto

    @GetMapping("/existsById/{id}")
    fun movieExistsById(@PathVariable id: Long): Boolean

    @GetMapping("/all")
    fun getAllMovies(): List<MovieDto>
}