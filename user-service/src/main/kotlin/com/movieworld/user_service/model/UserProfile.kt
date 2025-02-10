package com.movieworld.user_service.model

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.ForeignKey
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToMany
import jakarta.persistence.OneToOne
import jakarta.persistence.Table

@Entity
@Table(name = "user_profiles")
data class UserProfile(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @OneToOne
    @JoinColumn(
        name = "user_id",
        nullable = false,
        foreignKey = ForeignKey(name = "fk_user_profiles_user_id")
    )
    val user: User,

    @OneToMany(
        mappedBy = "userProfile",
        cascade = [CascadeType.ALL],
        fetch = FetchType.LAZY
    )
    val watchHistory: List<WatchHistoryEntry>,

    @OneToMany(
        mappedBy = "userProfile",
        cascade = [CascadeType.ALL],
        fetch = FetchType.LAZY
    )
    val ratings: List<Rating>
) {
    fun toDto(): UserProfileDto {
        return UserProfileDto(
            id = id,
            user = user.toDto(),
            watchHistory = watchHistory.map { it.toDto() },
            ratings = ratings.map { it.toDto() }
        )
    }
}
