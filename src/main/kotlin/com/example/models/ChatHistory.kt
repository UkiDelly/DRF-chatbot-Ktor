package com.example.models

import com.example.common.Role
import com.example.database.entity.ChatHistoryEntity
import com.example.plugins.LocalDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime


@Serializable
data class ChatHistory(
  val id: Int,
  val message: String,
  val role: Role,
  @Serializable(with = LocalDateTimeSerializer::class)
  val createdAt: LocalDateTime

) {
  constructor(entity: ChatHistoryEntity) : this(
    id = entity.id.value,
    message = entity.message,
    role = entity.role,
    createdAt = entity.createdAt
  )
}
