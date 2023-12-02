package com.example.routing.account

import com.example.common.TokenType
import com.example.plugins.getUserId
import com.example.plugins.withAuth
import io.ktor.server.application.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.Route

fun Route.accountRouting() {
	
	// 회원가입
	post<AccountRoutes.Register> {
		call.respondText { "Register!" }
	}
	
	// 로그인
	post<AccountRoutes.Login> { }
	
	
	
	withAuth(TokenType.refresh) {
		// 자동 로그인
		post<AccountRoutes.AutoLogin> {
			val userId = call.getUserId()
		}
		
		// 토큰 재발급
		post<AccountRoutes.RefreshToken> {
			val userId = call.getUserId()
		}
	}
	
}