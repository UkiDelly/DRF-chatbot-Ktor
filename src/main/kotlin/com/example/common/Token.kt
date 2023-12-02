package com.example.common

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.application.*
import kotlinx.serialization.Serializable
import toInstant
import java.time.LocalDateTime


enum class TokenType {
	access,
	refresh
}

@Serializable
data class Token(val accessToken: String, val refreshToken: String)


fun Application.generateToken(userId: Int): Token {
	
	val jwtIssuer: String = environment.config.property("jwt.issuer").getString()
	val jwtAudience: String = environment.config.property("jwt.audience").getString()
	val jwtSecret: String = environment.config.property("jwt.secret").getString()
	var expiredAt = LocalDateTime.now().plusDays(1)
	
	val accessToken = JWT.create()
		.withSubject(userId.toString())
		.withIssuer(jwtIssuer)
		.withClaim("type", TokenType.access.name)
		.withExpiresAt(expiredAt.toInstant())
		.sign(Algorithm.HMAC256(jwtSecret))
	
	
	expiredAt = LocalDateTime.now().plusMonths(3)
	
	val refreshToken = JWT.create()
		.withSubject(userId.toString())
		.withIssuer(jwtIssuer)
		.withClaim("type", TokenType.refresh.name)
		.withExpiresAt(expiredAt.toInstant())
		.sign(Algorithm.HMAC256(jwtSecret))
	
	
	return Token(accessToken, refreshToken)
	
	
}
