package com.example.routing.account

import io.ktor.server.resources.*
import io.ktor.server.routing.Route

fun Route.accountRouting() {
	
	// 회원가입
	post<AccountRoutes.Register> {}
	
	// 로그인
	post<AccountRoutes.Login> { }
	
	// 자동 로그인
	post<AccountRoutes.AutoLogin> { }
	
	// 토큰 재발급
	post<AccountRoutes.RefreshToken> { }
}