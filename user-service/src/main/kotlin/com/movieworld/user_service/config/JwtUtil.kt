package com.movieworld.user_service.config

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import java.util.*
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.security.Key

@Component
class JwtUtil {
    private val key: Key = Keys.secretKeyFor(SignatureAlgorithm.HS256)

    fun extractUsername(token: String?): String {
        return extractClaim(token) { it.subject }
    }

    fun extractExpiration(token: String?): Date {
        return extractClaim(token) { it.expiration }
    }

    fun <T> extractClaim(token: String?, claimsResolver: (Claims) -> T): T {
        val claims = extractAllClaims(token)
        return claimsResolver(claims)
    }

    private fun extractAllClaims(token: String?): Claims {
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .body
    }

    private fun isTokenExpired(token: String?): Boolean {
        return extractExpiration(token).before(Date())
    }

    fun generateToken(username: String): String {
        val claims: Map<String, Any?> = HashMap()
        return createToken(claims, username)
    }

    private fun createToken(claims: Map<String, Any?>, subject: String): String {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
            .signWith(SignatureAlgorithm.HS256, key).compact()
    }

    fun validateToken(token: String?, username: String): Boolean {
        val extractedUsername = extractUsername(token)
        return (extractedUsername == username && !isTokenExpired(token))
    }
}