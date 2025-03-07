package com.movieworld.recommendation_service

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients
class RecommendationServiceApplication

fun main(args: Array<String>) {
	println("DB_URL: ${System.getenv("DB_URL")}")
	println("DB_USER: ${System.getenv("DB_USER")}")
	println("DB_PASSWD: ${System.getenv("DB_PASSWD")}")
	runApplication<RecommendationServiceApplication>(*args)
}
