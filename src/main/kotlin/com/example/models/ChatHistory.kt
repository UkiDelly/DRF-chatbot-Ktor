package com.example.models

import com.example.common.Role
import com.example.database.entity.ChatHistoryEntity
import kotlinx.serialization.Serializable


@Serializable
data class ChatHistory(
  val id: Int,
  val message: String,
  val role: Role
) {
  constructor(entity: ChatHistoryEntity) : this(
    id = entity.id.value,
    message = entity.message,
    role = entity.role
  )
  
}
