package com.movieworld.recommendation_service.service

import com.movieworld.recommendation_service.model.movie.MovieDto
import com.movieworld.recommendation_service.model.user.UserDto
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(name = "user-service", url = "http://localhost:8081/api/v1/users")
interface UserServiceClient {

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: Long): UserDto

    @GetMapping("/email/{email}")
    fun getUserByEmail(@PathVariable email: String): UserDto
}

@FeignClient(name = "user-service", url = "http://localhost:8081/api/v1/movies")
interface MovieServiceClient {

    @GetMapping("/{id}")
    fun getMovieById(@PathVariable id: Long): MovieDto

    @GetMapping("/all")
    fun getAllMovies(): List<MovieDto>
}

