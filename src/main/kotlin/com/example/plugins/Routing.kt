package com.example.plugins

import com.example.common.TokenType
import com.example.routing.account.accountRouting
import com.example.routing.chat.chatRouting
import io.ktor.resources.*
import io.ktor.server.application.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.resources.Resources
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

fun Application.configureRouting() {
  install(Resources)
  
  routing {
    swaggerUI(path = "openapi")
    
    get("/") {
      call.respondText("Hello World!")
    }
    
    // 계정
    accountRouting()
    
    
    // 채팅
    withAuth(TokenType.access) {
      chatRouting()
    }
  }
}

@Serializable
@Resource("/articles")
class Articles(val sort: String? = "new")
