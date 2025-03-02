package com.movieworld.recommendation_service

import com.movieworld.movie_service.util.PostgresTestContainer
import org.junit.jupiter.api.Test
import org.springframework.boot.autoconfigure.flyway.FlywayProperties.Postgresql
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class RecommendationServiceApplicationTests: PostgresTestContainer()  {

	@Test
	fun contextLoads() {
	}

}
