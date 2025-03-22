package com.movieworld.recommendation_service.service

import com.movieworld.recommendation_service.model.movie.MovieDto
import com.movieworld.recommendation_service.model.user.UserDto
import com.movieworld.recommendation_service.model.user.UserProfileDto
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(name = "user-service", url = "\${url.user-service}")
interface UserServiceClient {

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: Long): UserDto

    @GetMapping("/email/{email}")
    fun getUserByEmail(@PathVariable email: String): UserDto
}

@FeignClient(name = "user-profile-service", url = "\${url.user-profile-service}")
interface UserProfileServiceClient {

    @GetMapping("/{id}")
    fun getUserProfileById(@PathVariable id: Long): UserProfileDto

    @GetMapping("/email/{email}")
    fun getUserProfileByEmail(
        @PathVariable email: String
    ): UserProfileDto
}

@FeignClient(name = "movie-service", url = "\${url.movie-service}")
interface MovieServiceClient {

    @GetMapping("/{id}")
    fun getMovieById(@PathVariable id: Long): MovieDto

    @GetMapping("/all")
    fun getAllMovies(): List<MovieDto>
}

