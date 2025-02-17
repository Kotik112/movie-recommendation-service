package com.movieworld.movie_service.util

import org.junit.Test
import kotlin.test.assertEquals

class TimeServiceTest {

    @Test
    fun `test getTimeWindowForYear`() {
        val timeWindow = TimeService.getTimeWindowForYear(2021)
        assertEquals("2021-01-01", timeWindow.startDate.toString())
        assertEquals("2021-12-31", timeWindow.endDate.toString())
    }
}