package com.example.service

import com.example.common.Role
import com.example.database.entity.*
import com.example.database.table.ChatHistoryTable
import com.example.database.table.ChatRoomTable
import com.example.database.table.SystemPromptTable
import com.example.models.ChatHistory
import com.example.models.ChatRoom
import com.example.models.ChatRoomDetail
import com.example.models.reqeust.ChatRoomCreateReqeustDto
import com.example.plugins.DatabaseFactory.query
import io.ktor.server.plugins.*
import org.koin.core.annotation.Module


@Module
class ChatService {
  
  suspend fun listChatRoom(userId: Int): List<ChatRoom> {
    return query {
      val chatRoomsEntity = ChatRoomEntity.find { ChatRoomTable.userId eq userId }.toList()
      chatRoomsEntity.map { ChatRoom(it) }
    }
    
  }
  
  suspend fun createChatRoom(userId: Int, reqeust: ChatRoomCreateReqeustDto): ChatRoomDetail {
    val chatRoom = query {
      val user = UserEntity.findById(userId) ?: throw NotFoundException("유저가 존재하지 않습니다.")
      ChatRoomDetailEntity.new {
        name = reqeust.name
        this.user = user
      }
    }
    
    return query {
      SystemPromptEntity.new {
        this.chatRoom = chatRoom
        this.prompt = reqeust.systemPrompt
      }
      ChatRoomDetail(chatRoom)
    }
  }
  
  suspend fun getChatRoom(chatRoomId: Int): ChatRoomDetail {
    return query {
      val chatRoom = findChatRoom(chatRoomId)
      ChatRoomDetail(chatRoom)
    }
  }
  
  
  suspend fun updateChatRoom(chatRoomId: Int, name: String) {
    query {
      val chatRoom = findChatRoom(chatRoomId)
      chatRoom.name = name
    }
  }
  
  suspend fun deleteChatRoom(chatRoomId: Int) {
    query {
      val chatRoom = findChatRoom(chatRoomId)
      chatRoom.delete()
    }
  }
  
  
  suspend fun addChatHistory(chatRoomId: Int, message: String, role: Role): ChatHistory {
    val chatHistory = query {
      val chatRoom = findChatRoom(chatRoomId)
      ChatHistoryEntity.new {
        this.chatRoom = chatRoom
        this.message = message
        this.role = role
      }
    }
    return ChatHistory(chatHistory)
  }
  
  suspend fun listChatHitory(chatRoomId: Int): List<ChatHistory> {
    return query {
      val chatHistories = ChatHistoryEntity.find { ChatHistoryTable.chatRoomId eq chatRoomId }.toList()
      chatHistories.map { ChatHistory(it) }
    }
  }
  
  suspend fun addSystemPrompt(chatRoomId: Int, prompt: String) {
    query {
      val chatRoom = findChatRoom(chatRoomId)
      SystemPromptEntity.new {
        this.chatRoom = chatRoom
        this.prompt = prompt
      }
    }
  }
  
  
  suspend fun updateSystemPrompt(chatRoomId: Int, prompt: String) {
    query {
      val systemPrompt =
        SystemPromptEntity.find { SystemPromptTable.chatRoomId eq chatRoomId }.firstOrNull() ?: throw NotFoundException(
          "채팅방이 존재하지 않습니다."
        )
      systemPrompt.prompt = prompt
    }
  }
  
  
  suspend fun deleteSystemPrompt(chatRoomId: Int) {
    query {
      val systemPrompt =
        SystemPromptEntity.find { SystemPromptTable.chatRoomId eq chatRoomId }.firstOrNull() ?: throw NotFoundException(
          "채팅방이 존재하지 않습니다."
        )
      systemPrompt.delete()
    }
  }
  
  
  private fun findChatRoom(chatRoomId: Int): ChatRoomDetailEntity {
    return ChatRoomDetailEntity.findById(chatRoomId) ?: throw NotFoundException("채팅방이 존재하지 않습니다.")
  }
}