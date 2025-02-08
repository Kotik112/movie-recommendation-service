package com.movieworld.movie_service.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDate

@Entity
@Table(name = "movie")
data class Movie(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column(name = "title", nullable = false, unique = true)
    val title: String,
    @Column(name = "genre", nullable = false)
    val genre: String,
    @Column(name = "release_date", nullable = false)
    val releaseDate: LocalDate,
    @Column(name = "director", nullable = false)
    val director: String,
    @Column(name = "description", nullable = false)
    val description: String
) {
    fun toDto(): MovieDto {
        return MovieDto(
            id = id,
            title = title,
            genre = genre,
            releaseDate = releaseDate,
            director = director,
            description = description
        )
    }
}
