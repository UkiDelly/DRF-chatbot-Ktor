package com.example.plugins

import com.example.service.ChatService
import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import org.koin.ktor.ext.inject
import java.time.Duration

fun Application.configureSockets() {
  install(WebSockets) {
    pingPeriod = Duration.ofSeconds(15)
    timeout = Duration.ofSeconds(15)
    maxFrameSize = Long.MAX_VALUE
    masking = false
  }
  routing {
    val chatService by inject<ChatService>()
    
    webSocket("/chat/{chatRoomId}") {
      val chatRoomId = call.parameters["chatRoomId"]?.toInt()
      
      if (chatRoomId == null) {
        close(CloseReason(CloseReason.Codes.NORMAL, "ChatRoomId is null."))
        return@webSocket
      }
      
      for (frame in incoming) {
        if (frame is Frame.Text) {
          val text = frame.readText()
          val receive = chatService.socket(chatRoomId, text)
          outgoing.send(Frame.Text(receive))
        }
      }
    }
  }
}
