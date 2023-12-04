package com.example.database.entity

import com.example.database.table.ChatHistoryTable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class ChatHistoryEntity(id: EntityID<Int>) : IntEntity(id) {
  companion object : IntEntityClass<ChatHistoryEntity>(ChatHistoryTable)
  
  var chatRoom by ChatRoomEntity referencedOn ChatHistoryTable.chatRoomId
  var message by ChatHistoryTable.message
  var role by ChatHistoryTable.role
  var createdAt by ChatHistoryTable.createdAt
  var updatedAt by ChatHistoryTable.updatedAt
  
}