package com.movieworld.recommendation_service.model

import jakarta.persistence.CollectionTable
import jakarta.persistence.Column
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.MapKeyColumn

@Entity
data class UserPreference(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val userId: Long,

    // Map genre -> weight/count (e.g., {"Action": 5, "Comedy": 3})
    @ElementCollection
    @CollectionTable(name = "user_genre_preferences", joinColumns = [JoinColumn(name = "user_id")])
    @MapKeyColumn(name = "genre")
    @Column(name = "preference_weight")
    val genrePreferences: Map<String, Int> = mutableMapOf(),

    // Map director -> weight/count (e.g., {"Spielberg": 2, "Nolan": 4})
    @ElementCollection
    @CollectionTable(name = "user_director_preferences", joinColumns = [JoinColumn(name = "user_id")])
    @MapKeyColumn(name = "director")
    @Column(name = "preference_weight")
    val directorPreferences: Map<String, Int> = mutableMapOf()
)