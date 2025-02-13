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
import org.hibernate.annotations.BatchSize
import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode

@Entity
@Table(name = "user_profiles")
data class UserProfile(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @OneToOne
    @JoinColumn(
        name = "user_id",
        nullable = false,
        foreignKey = ForeignKey(name = "fk_user_profiles_user_id")
    )
    val user: User,

    @OneToMany(
        mappedBy = "userProfile",
        fetch = FetchType.LAZY,
        cascade = [CascadeType.ALL]
    )
    @BatchSize(size = 20)
    @Fetch(FetchMode.SUBSELECT)
    var watchHistory: MutableSet<WatchHistoryEntry> = mutableSetOf(),

    @OneToMany(
        mappedBy = "userProfile",
        fetch = FetchType.LAZY,
        cascade = [CascadeType.ALL]
    )
    @BatchSize(size = 20)
    @Fetch(FetchMode.SUBSELECT)
    var ratings: MutableSet<Rating> = mutableSetOf()
) {
    fun toDto(): UserProfileDto {
        return UserProfileDto(
            id = id,
            user = user.toDto(),
            watchHistory = watchHistory.map { it.toDto() }.toMutableList(),
            ratings = ratings.map { it.toDto() }.toMutableList()
        )
    }

    override fun toString(): String {
        return "UserProfile(id=$id)"
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}