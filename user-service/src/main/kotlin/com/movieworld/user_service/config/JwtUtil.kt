package com.movieworld.user_service.config

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import java.util.*
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.security.Key

@Component
class JwtUtil {
    private val key: Key = Keys.secretKeyFor(SignatureAlgorithm.HS256)


    fun generateToken(userDetails: UserDetails): String {
        val claims: MutableMap<String, Any> = mutableMapOf()
        val role = userDetails.authorities.first().authority.removePrefix("ROLE_")
        claims["role"] = role
        return createToken(claims, userDetails.username)
    }

    private fun createToken(claims: Map<String, Any>, subject: String): String {
        return Jwts.builder()
            .setClaims(claims)
            .setSubject(subject)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()
    }

    fun extractEmail(token: String): String {
        return extractClaim(token, Claims::getSubject)
    }

    private fun <T> extractClaim(token: String, claimsResolver: (Claims) -> T): T {
        val claims = extractAllClaims(token)
        return claimsResolver(claims)
    }

    private fun extractAllClaims(token: String): Claims {
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .body
    }

    fun extractRole(token: String): String {
        val claims = extractAllClaims(token)
        return claims["role"] as String
    }

    fun validateToken(token: String, username: String): Boolean {
        val claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).body
        return claims.subject == username && !claims.expiration.before(Date())
    }
}