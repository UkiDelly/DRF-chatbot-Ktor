package com.example.models.reqeust

import kotlinx.serialization.Serializable


@Serializable
data class ChatRoomCreateReqeustDto(
  val name: String,
  val systemPrompt: String
)
@Serializable
data class UpdateChatRoomReqeustDto(
  val name: String
)