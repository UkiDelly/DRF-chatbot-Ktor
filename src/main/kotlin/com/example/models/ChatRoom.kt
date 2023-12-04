package com.example.models

import com.example.database.entity.ChatRoomEntity
import com.example.plugins.LocalDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime


@Serializable
data class ChatRoom(
  val id: Int,
  val name: String,
  val history: List<ChatHistory>,
  val prompts: List<SystemPrompt>,
  @Serializable(with = LocalDateTimeSerializer::class)
  val createdAt: LocalDateTime,
  @Serializable(with = LocalDateTimeSerializer::class)
  val updatedAt: LocalDateTime
) {
  
  constructor(entity: ChatRoomEntity) : this(
    id = entity.id.value,
    name = entity.name,
    prompts = entity.systemPrompt.map { SystemPrompt(it) },
    history = entity.history.map { ChatHistory(it) },
    createdAt = entity.createdAt,
    updatedAt = entity.updatedAt,
  )
}
