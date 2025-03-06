package com.movieworld.movie_service

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MovieServiceApplication

fun main(args: Array<String>) {
	println("DB_URL: ${System.getenv("DB_URL")}")
	println("DB_USER: ${System.getenv("DB_USER")}")
	println("DB_PASSWD: ${System.getenv("DB_PASSWD")}")
	runApplication<MovieServiceApplication>(*args)
}
