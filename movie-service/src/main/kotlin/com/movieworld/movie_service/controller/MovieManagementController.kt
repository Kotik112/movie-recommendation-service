package com.movieworld.movie_service.controller

import com.movieworld.movie_service.model.MovieDto
import com.movieworld.movie_service.service.impl.MovieManagementServiceImpl
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/movies")
class MovieManagementController(
    private val movieManagementService: MovieManagementServiceImpl
) {

    @GetMapping("/{title}")
    fun getMovie(@PathVariable("title") title: String): MovieDto {
        return movieManagementService.getMovie(title)
    }

    @GetMapping("/all")
    fun getMovies(): List<MovieDto> {
        return movieManagementService.getMovies()
    }

    @PostMapping
    fun addMovie(@RequestBody movieDto: MovieDto): MovieDto {
        return movieManagementService.addMovie(movieDto)
    }

    @PutMapping("/update/{title}")
    fun updateMovie(@PathVariable("title") title: String, @RequestBody movieDto: MovieDto): Boolean {
        return movieManagementService.updateMovie(title, movieDto)
    }

    @DeleteMapping("/{title}")
    fun deleteMovie(@PathVariable("title") title: String): Boolean {
        return movieManagementService.deleteMovie(title)
    }
}