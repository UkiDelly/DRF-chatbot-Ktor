package com.example.routing.chat

import com.example.plugins.getUserId
import io.ktor.server.application.*
import io.ktor.server.resources.*
import io.ktor.server.routing.Route


fun Route.chatRouting() {
  
  get<ChatRoutes> {
    val userId = call.getUserId()
  }
  
  post<ChatRoutes> {
    val userId = call.getUserId()
  }
  
  
  get<ChatRoutes.Id> { id ->
    val userId = call.getUserId()
  }
  
  put<ChatRoutes.Id> {
    val userId = call.getUserId()
  }
  
  delete<ChatRoutes.Id> { id ->
    val userId = call.getUserId()
  }
  
  get<ChatRoutes.Id.History> { id ->
    val userId = call.getUserId()
  }
  
  
}