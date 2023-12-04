package com.example.routing.chat

import com.example.models.reqeust.ChatRoomCreateReqeustDto
import com.example.models.response.SuccessResponse
import com.example.plugins.getUserId
import com.example.service.ChatService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.Route
import org.koin.ktor.ext.inject


fun Route.chatRouting() {
  
  val chatService by inject<ChatService>()
  
  get<ChatRoutes> {
    val userId = call.getUserId()
    val chatRooms = chatService.listChatRoom(userId)
    call.respond(HttpStatusCode.OK, SuccessResponse(chatRooms))
  }
  
  post<ChatRoutes> {
    val userId = call.getUserId()
    val body = call.receive<ChatRoomCreateReqeustDto>()
    val chatRoom = chatService.createChatRoom(userId, body)
    call.respond(HttpStatusCode.OK, SuccessResponse(chatRoom))
  }
  
  
  get<ChatRoutes.Id> { ch ->
    val userId = call.getUserId()
    val chatRoom = chatService.getChatRoom(ch.id)
    call.respond(HttpStatusCode.OK, SuccessResponse(chatRoom))
  }
  
  put<ChatRoutes.Id> {
    val userId = call.getUserId()
  }
  
  delete<ChatRoutes.Id> { chatRoom ->
    val userId = call.getUserId()
  }
  
  get<ChatRoutes.Id.History> { chatRoom ->
    val userId = call.getUserId()
  }
  
  
}