package com.movieworld.user_service.service.impl

import com.movieworld.user_service.config.JwtUtil
import com.movieworld.user_service.model.User
import com.movieworld.user_service.model.UserDto
import com.movieworld.user_service.repository.UserRepository
import com.movieworld.user_service.service.UserService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val jwtUtil: JwtUtil,
    private val authenticationManager: AuthenticationManager,
    private val userDetailsService: UserDetailsService,
    private val passwordEncoder: PasswordEncoder
): UserService {

    override fun createUser(user: UserDto): UserDto {
        val userToSave = User(
            id = 0,
            firstName = user.firstName,
            lastName = user.lastName,
            email = user.email,
            password = user.password
        )
        val savedUser = userRepository.createUser(
            firstName = userToSave.firstName,
            lastName = userToSave.lastName,
            email = userToSave.email,
            password = userToSave.password
        )
        return savedUser.toDto()
    }

    override fun getUserById(id: Long): UserDto {
        val user = userRepository.findByUserId(id)
        if(user != null) {
            return user.toDto()
        } else {
            throw Exception("User not found")
        }
    }

    override fun getUserByEmail(email: String): UserDto {
        val user = userRepository.findByEmail(email)
        if(user != null) {
            return user.toDto()
        } else {
            throw Exception("User not found")
        }
    }

    override fun loadUserByUsername(email: String): UserDetails {
        val user = userRepository.findByEmail(email)
            ?: throw UsernameNotFoundException("User not found with email: $email")
        return org.springframework.security.core.userdetails.User(
            user.email, user.password, emptyList()
        )
    }


//    override fun updateUser(userToUpdate: UserDto): User {
//        TODO("Not yet implemented")
//    }
//
//    override fun deleteUser(id: Long) {
//        TODO("Not yet implemented")
//    }

    fun authenticate(email: String, password: String): String {
        authenticationManager.authenticate(UsernamePasswordAuthenticationToken(email, password))
        val userDetails: UserDetails = userDetailsService.loadUserByUsername(email)
        return jwtUtil.generateToken(userDetails.username)
    }
}