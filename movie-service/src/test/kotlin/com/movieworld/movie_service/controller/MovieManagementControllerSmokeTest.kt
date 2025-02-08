package com.movieworld.movie_service.controller

import junit.framework.TestCase.assertNotNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.Test

@SpringBootTest
class MovieManagementControllerSmokeTest(
    @Autowired
    private val movieManagementController: MovieManagementController
) {

    @Test
    fun contextLoads() {
        assertNotNull(movieManagementController)
    }
}