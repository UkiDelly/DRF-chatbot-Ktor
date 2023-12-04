package com.example.database.entity

import com.example.database.table.ChatHistoryTable
import com.example.database.table.ChatRoomTable
import com.example.database.table.SystemPromptTable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class ChatRoomDetailEntity(id: EntityID<Int>) : IntEntity(id) {
  companion object : IntEntityClass<ChatRoomDetailEntity>(ChatRoomTable)
  
  var name by ChatRoomTable.name
  var user by UserEntity referencedOn ChatRoomTable.userId
  val history by ChatHistoryEntity referrersOn ChatHistoryTable.chatRoomId
  val systemPrompt by SystemPromptEntity referrersOn SystemPromptTable.chatRoomId
  val createdAt by ChatRoomTable.createdAt
  var updatedAt by ChatRoomTable.updatedAt
}

class ChatRoomEntity(id: EntityID<Int>) : IntEntity(id) {
  companion object : IntEntityClass<ChatRoomEntity>(ChatRoomTable)
  
  var name by ChatRoomTable.name
  var user by UserEntity referencedOn ChatRoomTable.userId
  var createdAt by ChatRoomTable.createdAt
  var updatedAt by ChatRoomTable.updatedAt
}
