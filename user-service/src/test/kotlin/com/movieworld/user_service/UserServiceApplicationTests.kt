package com.movieworld.user_service

import com.movieworld.movie_service.util.PostgresTestContainer
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class UserServiceApplicationTests: PostgresTestContainer() {

	@Test
	fun contextLoads() {
	}

}
