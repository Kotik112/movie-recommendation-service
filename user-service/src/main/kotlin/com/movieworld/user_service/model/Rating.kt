package com.movieworld.user_service.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.ForeignKey
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import java.time.LocalDateTime

@Entity
@Table(
    name = "ratings",
    uniqueConstraints = [
        UniqueConstraint(
            columnNames = ["movie_id", "user_profile_id"]
        )
    ]
)
class Rating(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(
        name = "movie_id",
        nullable = false
    )
    val movieId: Long,

    @Column(
        name = "rating_value",
        nullable = false
    )
    @Min(0)
    @Max(10)
    val ratingValue: Int,

    @Column(
        name = "rated_at",
        nullable = false
    )
    val ratedAt: LocalDateTime,

    @ManyToOne
    @JoinColumn(
        name = "user_profile_id",
        nullable = false,
        foreignKey = ForeignKey(name = "fk_ratings_user_profile_id")
    )
    val userProfile: UserProfile
) {
    fun toDto(): RatingDto {
        return RatingDto(
            id = id,
            movieId = movieId,
            ratingValue = ratingValue,
            ratedAt = ratedAt
        )
    }
}
