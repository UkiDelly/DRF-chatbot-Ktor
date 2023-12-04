package com.example.openAi

import com.example.common.Role
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient


@Serializable
sealed class BaseModel(
  @Transient
  open val content: String = "",
  
  )


@Serializable
class SystemModel(override val content: String, val role: String = "system") : BaseModel()


@Serializable
class UserModel(override val content: String, val role: Role = Role.user) : BaseModel()

@Serializable
class AssistantModel(override val content: String, val role: Role = Role.assistant) : BaseModel()

@Serializable
class OpenApiRequest(
  val model: String,
  val messages: List<BaseModel>,
  val responseFormat: Map<String, String> = mapOf("type" to "json_object"),
)
