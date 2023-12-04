package com.example.models

import com.example.database.entity.SystemPromptEntity
import com.example.plugins.LocalDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime


@Serializable
data class SystemPrompt(
  val id: Int,
  val prompt: String,
  @Serializable(with = LocalDateTimeSerializer::class)
  val createdAt: LocalDateTime
) {
  
  constructor(entity: SystemPromptEntity) : this(
    id = entity.id.value,
    prompt = entity.prompt,
    createdAt = entity.createdAt
  )
}


