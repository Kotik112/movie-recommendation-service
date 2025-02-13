package com.movieworld.user_service.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "first_name", nullable = false)
    @NotBlank(message = "First name is required")
    private val firstName: String,

    @Column(name = "last_name", nullable = false)
    @NotBlank(message = "Last name is required")
    private val lastName: String,

    @Column(name = "email", nullable = false, unique = true)
    @Email(message = "Invalid email")
    val email: String,

    @Column(name = "password", nullable = false)
    @Size(min = 8, message = "Password must be at least 8 characters long")
    val password: String,
) {
    fun toDto(): UserDto {
        return UserDto(
            id = id ?: 0,
            firstName = firstName,
            lastName = lastName,
            email = email,
            password = password
        )
    }

    // TODO: Look to remove this method
    override fun hashCode(): Int {
        return id.hashCode()
    }
}