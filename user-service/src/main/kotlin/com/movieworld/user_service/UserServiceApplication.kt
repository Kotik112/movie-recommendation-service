package com.movieworld.user_service

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
class UserServiceApplication

fun main(args: Array<String>) {
//	println("DB_URL: ${System.getenv("DB_URL")}")
//	println("DB_USER: ${System.getenv("DB_USER")}")
//	println("DB_PASSWD: ${System.getenv("DB_PASSWD")}")
	runApplication<UserServiceApplication>(*args)
}
