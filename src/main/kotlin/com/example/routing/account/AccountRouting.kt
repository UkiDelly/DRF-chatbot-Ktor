package com.example.routing.account

import com.example.common.SocialType
import com.example.common.TokenType
import com.example.common.generateToken
import com.example.models.reqeust.LoginRequestDto
import com.example.models.reqeust.RegisterRequestDto
import com.example.models.response.UserInfoWithTokenResponseDto
import com.example.plugins.getUserId
import com.example.plugins.withAuth
import com.example.service.AccountService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.resources.post
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.accountRouting() {
  
  
  val accountService by inject<AccountService>()
  
  // 회원가입
  post<AccountRoutes.Register> {
    val body = call.receive<RegisterRequestDto>()
    val user = accountService.register(body)
    val token = application.generateToken(user.id)
    call.respond(HttpStatusCode.Created, UserInfoWithTokenResponseDto(user, token))
  }
  
  // 로그인
  post<AccountRoutes.Login> {
    val body = call.receive<LoginRequestDto>()
    val user = when (body.socialType) {
      SocialType.email -> accountService.findEmailUser(body.email, body.password !!)
      SocialType.google -> accountService.findSocialUser(body.snsId !!)
    }
    val token = application.generateToken(user.id)
    call.respond(HttpStatusCode.OK, UserInfoWithTokenResponseDto(user, token))
  }
  
  
  withAuth(TokenType.refresh) {
    // 자동 로그인
    post<AccountRoutes.AutoLogin> {
      val userId = call.getUserId()
      val user = accountService.findUserById(userId)
      val token = application.generateToken(user.id)
      call.respond(HttpStatusCode.OK, UserInfoWithTokenResponseDto(user, token))
    }
    
    // 토큰 재발급
    post<AccountRoutes.RefreshToken> {
      val userId = call.getUserId()
      val accessToken = application.generateToken(userId).accessToken
      call.respond(HttpStatusCode.OK, mapOf("access" to accessToken))
    }
  }
}