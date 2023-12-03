package com.example.plugins

import com.auth0.jwt.exceptions.JWTVerificationException
import com.example.common.ConflictException
import com.example.models.response.ErrorResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.requestvalidation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

fun Application.configureStatusPage() {
  install(StatusPages) {
    
    // JWT 검증 실패
    exception<JWTVerificationException> { call, cause ->
      call.respond(HttpStatusCode.Unauthorized, ErrorResponse(error = cause.message ?: "검증된 토큰이 아닙니다."))
    }
    
    // Conflict 에러
    exception<ConflictException> { call, cause ->
      call.respond(HttpStatusCode.Conflict, ErrorResponse(cause.message !!))
    }
    
    // Request Validation 에서
    exception<RequestValidationException> { call, cause ->
      call.respond(HttpStatusCode.BadRequest, ErrorResponse(cause.reasons))
    }
  }
}