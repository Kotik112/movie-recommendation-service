package com.movieworld.recommendation_service.service

import com.movieworld.recommendation_service.model.UserPreference
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class UserPreferenceService(
    @Autowired
    private val restTemplate: RestTemplate
) {
    fun getUserPreferences(userId: Long): UserPreference? {
        val url = "http://localhost:8080/api/v1/users/$userId/preferences"
        return restTemplate.getForObject(url, UserPreference::class.java)
    }
}