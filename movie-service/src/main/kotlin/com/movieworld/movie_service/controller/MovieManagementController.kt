package com.movieworld.movie_service.controller

import com.movieworld.movie_service.model.MovieDto
import com.movieworld.movie_service.service.impl.MovieManagementServiceImpl
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/movies")
class MovieManagementController(
    private val movieManagementService: MovieManagementServiceImpl
) {

    @PostMapping
    fun addMovie(@RequestBody movieDto: MovieDto): MovieDto {
        return movieManagementService.addMovie(movieDto)
    }
}