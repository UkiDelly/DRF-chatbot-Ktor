package com.example.database.entity

import com.example.database.table.SystemPromptTable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class SystemPromptEntity(id: EntityID<Int>) : IntEntity(id) {
  
  companion object : IntEntityClass<SystemPromptEntity>(SystemPromptTable)
  
  var prompt by SystemPromptTable.prompt
  var chatRoom by ChatRoomEntity referencedOn SystemPromptTable.chatRoomId
  var createdAt by SystemPromptTable.createdAt
  
}