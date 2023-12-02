package com.example.plugins

import com.auth0.jwt.exceptions.JWTVerificationException
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

fun Application.configureStatusPage() {
	install(StatusPages) {
		
		// JWT 검증 실패
		exception<JWTVerificationException> { call, cause ->
			call.respond(HttpStatusCode.Unauthorized, cause.message ?: "검증된 토큰이 아닙니다.")
		}
		
		exception<Throwable> { call, cause ->
			call.respondText(text = "500: $cause", status = HttpStatusCode.InternalServerError)
		}
	}
}