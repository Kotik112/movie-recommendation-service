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
@Suppress("unused")
class MovieManagementController(
    private val movieManagementService: MovieManagementServiceImpl
) {
    @GetMapping("/id/{id}")
    fun getMovieById(@PathVariable("id") id: Long): MovieDto {
        return movieManagementService.findMovieById(id = id)
    }

    @GetMapping("/{title}")
    fun getMovie(@PathVariable("title") title: String): MovieDto {
        return movieManagementService.getMovie(title = title)
    }

    @GetMapping("/all")
    fun getMovies(): List<MovieDto> {
        return movieManagementService.getAllMovie()
    }

    @PostMapping
    fun addMovie(@RequestBody movieDto: MovieDto): MovieDto {
        return movieManagementService.addMovie(movieDto = movieDto)
    }

    @PutMapping("/update/{id}")
    fun updateMovie(@PathVariable("id") movieId: Long, @RequestBody movieDto: MovieDto): Boolean {
        return movieManagementService.updateMovie(movieId = movieId, movieDto = movieDto)
    }

    @DeleteMapping("/{title}")
    fun deleteMovie(@PathVariable("title") title: String): Boolean {
        return movieManagementService.deleteMovie(title = title)
    }

    @GetMapping("/existsById/{id}")
    fun movieExistsById(@PathVariable("id") movieId: Long): Boolean {
        return movieManagementService.movieExistsById(movieId = movieId)
    }

    @GetMapping("/existsByTitle/{title}")
    fun movieExistsByTitle(@PathVariable("title") title: String): Boolean {
        return movieManagementService.movieExistsByTitle(title = title)
    }
}