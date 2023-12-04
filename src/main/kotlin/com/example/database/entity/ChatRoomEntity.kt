package com.example.database.entity

import com.example.database.table.ChatRoomTable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class ChatRoomEntity(id: EntityID<Int>) : IntEntity(id) {
  companion object : IntEntityClass<ChatRoomEntity>(ChatRoomTable)
  
  var name by ChatRoomTable.name
  var user by UserEntity referencedOn ChatRoomTable.userId
  val history by ChatHistoryEntity referrersOn ChatRoomTable.id
  val systemPrompt by SystemPromptEntity referrersOn ChatRoomTable.id
  val createdAt by ChatRoomTable.createdAt
  var updatedAt by ChatRoomTable.updatedAt
}