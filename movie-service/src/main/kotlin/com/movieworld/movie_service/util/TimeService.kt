package com.movieworld.movie_service.util

import java.time.LocalDate
import java.time.Year

data class TimeWindow(val startDate: LocalDate, val endDate: LocalDate)

class TimeService {

    companion object {
        fun getTimeWindowForYear(yearAsInt: Int): TimeWindow {
            return try {
                val year = Year.of(yearAsInt)
                val startDate = LocalDate.of(year.value, 1, 1)
                val endDate = LocalDate.of(year.value, 12, 31)
                return TimeWindow(startDate, endDate)
            }
            catch (e: Exception) {
                throw IllegalArgumentException("Invalid year: $yearAsInt")
            }
        }
    }
}