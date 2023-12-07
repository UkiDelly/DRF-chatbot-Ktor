package com.example.plugins

import com.example.openAi.OpenAiClient
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
			
			
			val openAiToken = application.environment.config.property("openai.token").getString()
			val openAiClient = OpenAiClient(openAiToken)
			
			if (chatRoomId == null) {
				close(CloseReason(CloseReason.Codes.NORMAL, "ChatRoomId is null."))
				return@webSocket
			}
			
			for (frame in incoming) {
				if (frame is Frame.Text) {
					val text = frame.readText()
					// DB에 데이터 추가
					// chatService.socket(chatRoomId, text)
					
					// DB에서 채팅방의 채팅 내역 조회
					val messages = chatService.getAllMessages(chatRoomId)
					// 챗지피티 대답
					val res = openAiClient.sendChat(messages)
					// DB에 대답 저장
					chatService.addAssistantMessage(chatRoomId, res)
					// 웹소켓에 대답 보내기
					outgoing.send(Frame.Text("하이"))
				}
			}
		}
	}
}
